package lqh.kframe.network

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 功能：网络请求 Retrofit 基本配置
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class RetrofitFactory private constructor() {

    companion object {

        // 域名
        private var BASE_URL = ""
        // 请求超时时间
        private var TIME_OUT = 0L
        // 是否测试/开发环境
        private var NO_PRO = true

        private var factory: RetrofitFactory? = null
            get() {
                if (field == null) {
                    field = RetrofitFactory()
                }
                return field
            }

        @Synchronized
        fun get(): RetrofitFactory? {
            return factory
        }

        /**
         * 构建打印日志拦截器
         */
        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger {
                    // TODO 编写工具类
                    Log.e("abc", it)
                })
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        private fun buildGson(): Gson {
            return GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create()
        }

        private var client: OkHttpClient? = null
        private fun getClient(): OkHttpClient {
            if (null == client) {
                @Synchronized
                if (null === client) {
                    val builder = OkHttpClient.Builder()
                    client = builder.addNetworkInterceptor(getHttpLoggingInterceptor())
                        .addInterceptor { chain ->
                            // 添加公共的头部
                            val request = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json")
                                .build()
                            chain.proceed(request)
                        }
                        // 失败重试
                        .retryOnConnectionFailure(false)
                        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .build()
                }
            }
            return client!!
        }

        private var retrofit: Retrofit? = null
        fun getRetrofit(): Retrofit {
            if (null == retrofit) {
                @Synchronized
                if (null == retrofit) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        // 添加 GSON 转换器
                        .addConverterFactory(GsonConverterFactory.create(buildGson()))
                        // 添加 RxJava 2 支持
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(getClient())
                        .build()
                }
            }
            return retrofit!!
        }

        fun <T> getRetroService(service: Class<T>): T {
            return getRetrofit().create(service)
        }

    }

    fun init(baseUrl: String, timeOut: Long, noPro: Boolean) {
        BASE_URL = baseUrl
        TIME_OUT = timeOut
        NO_PRO = noPro
    }

}
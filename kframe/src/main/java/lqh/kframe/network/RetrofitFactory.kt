package lqh.kframe.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import lqh.kframe.network.livedata.LiveDataCallAdapterFactory
import lqh.kframe.util.LogUtils
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
        // 是否生产环境
        private var IS_PRO = true

        val INSTANCE: RetrofitFactory by lazy {
            RetrofitFactory()
        }

        /**
         * 构建打印日志拦截器
         */
        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                LogUtils.d(it)
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        private fun buildGson(): Gson {
            return GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create()
        }

        private val client: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .addNetworkInterceptor(getHttpLoggingInterceptor())
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

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                // 添加 GSON 转换器
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                // 添加 RxJava 2 支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }

        fun <T> getRetroService(service: Class<T>): T {
            return retrofit.create(service)
        }

        private val liveDataRetrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                // 添加 GSON 转换器
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client)
                .build()
        }

        fun <T> getLiveDataRetroService(service: Class<T>): T {
            return liveDataRetrofit.create(service)
        }

    }

    /**
     * @param baseUrl 域名
     * @param timeOut 请求超时时间
     * @param isPro 是否正式环境
     */
    fun init(baseUrl: String, timeOut: Long, isPro: Boolean) {
        BASE_URL = baseUrl
        TIME_OUT = timeOut
        IS_PRO = isPro
    }

}
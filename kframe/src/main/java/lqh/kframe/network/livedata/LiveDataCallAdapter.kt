package lqh.kframe.network.livedata

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<T>> {
    override fun adapt(call: Call<T>): LiveData<T> = object : LiveData<T>() {
        // 多线程中保证线程安全
        private val started = AtomicBoolean(false)

        override fun onActive() {
            super.onActive()
            // 确保执行一次
            if (started.compareAndSet(false, true)) {
                call.enqueue(object : Callback<T> {
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        postValue(null)
                    }

                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        postValue(response.body())
                    }

                })
            }
        }
    }

    override fun responseType(): Type = responseType
}
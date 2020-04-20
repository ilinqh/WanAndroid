package lqh.kframe.network.livedata

import androidx.lifecycle.LiveData
import lqh.kframe.network.HttpResponse
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

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
class LiveDataCallAdapterFactory : Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        val responseType: Type
        check(getRawType(returnType) == LiveData::class.java) { "return type must be parameterized" }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(observableType)
        responseType = if (rawType == Response::class.java) {
            require(observableType is ParameterizedType) { "Response must be parameterized" }
            getParameterUpperBound(0, observableType)
        } else {
            observableType
        }
        return LiveDataCallAdapter<Any>(responseType)
    }
}
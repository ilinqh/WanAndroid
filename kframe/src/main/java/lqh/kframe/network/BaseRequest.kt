package lqh.kframe.network

import android.content.Context
import android.text.TextUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import lqh.kframe.R
import lqh.kframe.util.NetworkUtils
import lqh.kframe.util.TipUtils

/**
 * 功能：发起网络请求的基类，泛型 T 是数据返回的实体类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/3
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class BaseRequest<T, E> constructor(val context: Context, var isShowDialog: Boolean = true) {

    private val params by lazy {
        HashMap<String, Any>()
    }

    var retroService: T? = null

    /**
     * 没有网络
     */
    private val NO_NETWORK = 0

    fun addLimit(limit: Long = 10) {
        addParams("limit", limit)
    }

    fun addOffset(offset: Long) {
        addParams("offset", offset)
    }

    fun addParams(key: String, value: Any) {
        val strValue = value.toString()
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(strValue)) {
            return
        }
        params[key] = strValue
    }

    fun request(service: Class<T>, listener: RequestListener<E>) {
        if (!NetworkUtils.isNetworkConnect(context)) {
            val message = context.getString(R.string.tip_please_check_network_connect_first)
            listener.onRequestFailure(NO_NETWORK, message, null)
        } else {
            retroService = RetrofitFactory.getRetroService(service)

            Observable.just(params)
                .flatMap {
                    listener.apply(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<E>(context, isShowDialog) {
                    override fun requestSuccess(data: E?) {
                        listener.onRequestSuccess(data)
                    }

                    override fun requestFail(errorCode: Int, errorMsg: String, data: E?) {
                        if (errorCode == RESPONSE_CODE_ERROR) {
                            TipUtils.showToast(context, errorMsg)
                        } else {
                            listener.onRequestFailure(errorCode, errorMsg, data)
                        }
                    }

                })
        }
    }

    /**
     * 普通网络请求
     */
    interface RequestListener<E> {
        /**
         * 发送网络请求
         */
        fun apply(params: Map<String, Any>): Observable<HttpResponse<E>>

        /**
         * 请求成功
         */
        fun onRequestSuccess(response: E?)

        /**
         * 请求失败
         */
        fun onRequestFailure(code: Int, message: String, data: E?)
    }

}

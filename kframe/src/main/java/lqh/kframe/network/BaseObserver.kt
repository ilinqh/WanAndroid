package lqh.kframe.network

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import lqh.kframe.R
import lqh.kframe.util.LogUtils
import retrofit2.HttpException

/**
 * 功能：网络请求观察者的基类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/3
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
abstract class BaseObserver<T>(var context: Context, showProgress: Boolean) :
    SingleObserver<HttpResponse<T>> {

    companion object {
        /**
         * 代表执行成功
         */
        private const val RESPONSE_CODE_SUCCESS = 0
        /**
         *  代表登录失效
         */
        const val RESPONSE_CODE_ERROR = -1001
    }

    private var dialog: Dialog? = null

    private var code = -1
    private var message: String = "未知的错误"

    init {
        if (showProgress) {
            dialog = createDialog()
            dialog?.show()
        }
    }

    /**
     * 创建加载中弹窗
     *
     * @return
     */
    private fun createDialog(): Dialog {
        val loadingDialog = Dialog(context, R.style.AppTheme_OutsideUnCloseDialog)
        val loadingView = LayoutInflater.from(context).inflate(R.layout.layout_loading_status, null, false)
        loadingDialog.setCancelable(false)
        loadingDialog.setContentView(loadingView)
        return loadingDialog
    }

    override fun onSubscribe(d: Disposable) {}

    override fun onSuccess(response: HttpResponse<T>) {
        dismissDialog()
        try {
            if (response.errorCode == RESPONSE_CODE_SUCCESS) {
                requestSuccess(response.data)
            } else {
                requestFail(response.errorCode, response.errorMsg, response.data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onError(e: Throwable) {
        dismissDialog()
        if (e is HttpException) {
            code = e.code()
            message = e.message()
            if (TextUtils.equals(message, "HTTP 502 Bad Gateway")) {
                message = "系统正在更新，请稍后重试"
            }
        } else {
            message = e.message ?: "未知的错误"
        }
        LogUtils.e("Error--> $message")

        requestFail(code, message, null)
    }

    /**
     * 请求成功
     */
    abstract fun requestSuccess(data: T?)

    /**
     * 请求失败
     */
    abstract fun requestFail(errorCode: Int, errorMsg: String, data: T?)

    private fun dismissDialog() {
        dialog?.dismiss()
    }
}
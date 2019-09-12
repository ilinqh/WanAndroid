package lqh.kframe.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * 功能：日志打印工具类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object LogUtils {

    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)

    val now = {
        dateFormat.format(Date(System.currentTimeMillis()))
    }

    var debug = true

    var TAG = "KFrame"

    fun v(msg: String) {
        if (debug) {
            Log.v(TAG, "${now()} [${Thread.currentThread().name}] $msg")
        }
    }

    fun d(msg: String) {
        if (debug) {
            Log.d(TAG, "${now()} [${Thread.currentThread().name}] $msg")
        }
    }

    fun i(msg: String) {
        if (debug) {
            Log.i(TAG, "${now()} [${Thread.currentThread().name}] $msg")
        }
    }

    fun w(msg: String) {
        if (debug) {
            Log.w(TAG, "${now()} [${Thread.currentThread().name}] $msg")
        }
    }

    fun e(msg: String) {
        if (debug) {
            Log.e(TAG, "${now()} [${Thread.currentThread().name}] $msg")
        }
    }
}
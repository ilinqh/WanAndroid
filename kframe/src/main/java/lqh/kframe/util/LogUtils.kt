package lqh.kframe.util

import android.util.Log

/**
 * 功能：日志打印工具类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class LogUtils {

    companion object {
        var debug = false

        var TAG = "KFrame"

        fun v(msg: String) {
            if (debug) {
                Log.v(TAG, msg)
            }
        }

        fun d(msg: String) {
            if (debug) {
                Log.d(TAG, msg)
            }
        }

        fun i(msg: String) {
            if (debug) {
                Log.i(TAG, msg)
            }
        }

        fun w(msg: String) {
            if (debug) {
                Log.w(TAG, msg)
            }
        }

        fun e(msg: String) {
            if (debug) {
                Log.e(TAG, msg)
            }
        }
    }

}
package lqh.kframe.util

import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue
import android.view.WindowManager

/**
 * 功能：屏幕相关工具类，dp/px，sp/px 转换
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */

/**
 * dp -> px
 */
fun Int.dp2px() =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * px -> dp
 */
fun Int.px2dp() =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * sp -> px
 */
fun Int.sp2px() =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * px -> sp
 */
fun Int.px2sp() =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

class UIUtils {

    companion object {

        /**
         * 获取屏幕宽度
         */
        @JvmStatic
        fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

        /**
         * 获取屏幕高度
         */
        @JvmStatic
        fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

        /**
         * 获取 StatusBar 高度
         */
        @JvmStatic
        fun getStatusBarHeight(): Int {
            var result = 0
            val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId)
            }
            return result
        }

        /**
         * 判断 Activity 是否全屏
         */
        fun isFullScreen(activity: Activity): Boolean {
            val flag = activity.window.attributes.flags
            return (flag and WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN
        }

        private const val FAST_CLICK_DELAY_TIME = 500

        private var lastClickTime = 0L

        /**
         * 判断是否快速点击
         */
        fun isFastClick(): Boolean {
            var flag = false
            val currentClickTime = System.currentTimeMillis()
            if (currentClickTime - lastClickTime < FAST_CLICK_DELAY_TIME) {
                flag = true
            }
            lastClickTime = currentClickTime
            return flag
        }
    }

}
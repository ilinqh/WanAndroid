package lqh.kframe.util

import android.content.Context

/**
 * 功能：屏幕相关工具类，dp/px，sp/px 转换
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class ScreenUtils {

    companion object {
        /**
         * dp -> px
         */
        fun Int.dp2px(context: Context) = (this * context.resources.displayMetrics.density + 0.5f).toInt()

        /**
         * px -> dp
         */
        fun Int.px2dp(context: Context) =
            (this / context.resources.displayMetrics.density + 0.5f).toInt()

        /**
         * sp -> px
         */
        fun Int.sp2px(context: Context) =
            (this * context.resources.displayMetrics.scaledDensity + 0.5f).toInt()

        /**
         * px -> sp
         */
        fun Int.px2sp(context: Context) =
            (this / context.resources.displayMetrics.scaledDensity + 0.5f).toInt()

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(context: Context) = context.resources.displayMetrics.widthPixels

        /**
         * 获取屏幕高度
         */
        fun getScreenHeight(context: Context) = context.resources.displayMetrics.heightPixels

        /**
         * 获取 StatusBar 高度
         */
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }

}
package lqh.kframe.util

import android.content.res.Resources
import android.util.TypedValue

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

class ScreenUtils {

    companion object {

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

        /**
         * 获取屏幕高度
         */
        fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

        /**
         * 获取 StatusBar 高度
         */
        fun getStatusBarHeight(): Int {
            var result = 0
            val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId)
            }
            return result
        }
    }

}
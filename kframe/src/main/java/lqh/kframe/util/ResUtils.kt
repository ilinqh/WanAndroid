package lqh.kframe.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * 功能：资源工具类，根据资源 ID 快速获取资源
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class ResUtils {

    companion object {
        /**
         * 获取文字
         */
        @JvmOverloads
        fun getString(context: Context, @StringRes resId: Int, args: Any? = null) =
            context.resources.getString(resId, args)

        /**
         * 获取颜色
         */
        fun getColor(context: Context, @ColorRes resId: Int) = ContextCompat.getColor(context, resId)

        /**
         * 获取 Drawable
         */
        fun getDrawable(context: Context, @DrawableRes resId: Int) =
            ContextCompat.getDrawable(context, resId)

        
    }

}
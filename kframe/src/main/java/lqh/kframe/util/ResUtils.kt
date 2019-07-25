package lqh.kframe.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.*
import androidx.core.content.ContextCompat

/**
 * 功能：资源工具类，根据资源 ID 快速获取资源
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
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
        @JvmStatic
        fun getString(@StringRes resId: Int, args: Any? = null) = Resources.getSystem().getString(resId, args)

        /**
         * 获取颜色
         */
        @JvmStatic
        fun getColor(context: Context, @ColorRes resId: Int) = ContextCompat.getColor(context, resId)

        /**
         * 获取 Drawable
         */
        @JvmStatic
        fun getDrawable(context: Context, @DrawableRes resId: Int) =
            ContextCompat.getDrawable(context, resId)

        /**
         * 获取 selector
         *
         * @param normalDrawable    正常状态下的 drawable
         * @param stateDrawable     非正常状态下的 drawable
         * @param state             selector 状态值
         */
        @JvmStatic
        fun getDrawableStateList(
            normalDrawable: Drawable,
            stateDrawable: Drawable,
            @AttrRes state: Int
        ): StateListDrawable {
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(state), stateDrawable)
            stateListDrawable.addState(intArrayOf(), normalDrawable)
            return stateListDrawable
        }

        /**
         * 获取按压 selector
         *
         * @param normalDrawable  正常状态 Drawable
         * @param pressedDrawable 按压状态 Drawable
         */
        @JvmStatic
        fun getPressedStateList(normalDrawable: Drawable, pressedDrawable: Drawable): StateListDrawable =
            getDrawableStateList(normalDrawable, pressedDrawable, android.R.attr.state_pressed)

        /**
         * 获取选中 selector
         *
         * @param normalDrawable  正常状态 Drawable
         * @param checkedDrawable 选中状态 Drawable
         */
        @JvmStatic
        fun getCheckedStateList(normalDrawable: Drawable, checkedDrawable: Drawable): StateListDrawable =
            getDrawableStateList(normalDrawable, checkedDrawable, android.R.attr.state_checked)

        /**
         * 获取是否可用 selector
         *
         * @param normalDrawable  正常状态 Drawable
         * @param unableDrawable  不可用状态 Drawable
         */
        @JvmStatic
        fun getEnabledStateList(normalDrawable: Drawable, unableDrawable: Drawable): StateListDrawable =
            getDrawableStateList(normalDrawable, unableDrawable, android.R.attr.state_enabled)

        /**
         * 获取 shape
         *
         * @param radius      弧度
         * @param fillColor   填充颜色
         * @param strokeWidth 边框宽度
         * @param strokeColor 边框填充色
         * @param width       shape 宽度
         * @param height      shape 高度
         */
        @JvmStatic
        @JvmOverloads
        fun getShape(
            radius: Float,
            @ColorInt fillColor: Int,
            strokeWidth: Int,
            @ColorInt strokeColor: Int,
            width: Int? = null,
            height: Int? = null
        ): GradientDrawable {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.cornerRadius = radius
            gradientDrawable.setColor(fillColor)
            if (width != null && height != null) {
                gradientDrawable.setSize(width, height)
            }
            gradientDrawable.setStroke(strokeWidth, strokeColor)
            return gradientDrawable
        }

        /**
         * 获取字体颜色 selector
         *
         * @param normalColor 正常状态 Color
         * @param stateColor  非正常状态 Color
         * @param state       selector 状态值
         * @return
         */
        @JvmStatic
        fun getColorStateList(
            context: Context,
            @ColorRes normalColor: Int,
            @ColorRes stateColor: Int,
            @AttrRes state: Int
        ): ColorStateList {
            val states = arrayOfNulls<IntArray>(2)
            states[0] = intArrayOf(state)
            states[1] = intArrayOf()
            val colors = intArrayOf(getColor(context, stateColor), getColor(context, normalColor))
            return ColorStateList(states, colors)
        }

        /**
         * 获取按压字体颜色 selector
         *
         * @param normalColor   正常状态 Color
         * @param pressedColor  按压状态 Color
         * @return
         */
        @JvmStatic
        fun getPressedStateList(context: Context, @ColorRes normalColor: Int, @ColorRes pressedColor: Int): ColorStateList =
            getColorStateList(context, normalColor, pressedColor, android.R.attr.state_pressed)

        /**
         * 获取选中字体颜色 selector
         *
         * @param normalColor   正常状态 Color
         * @param checkedColor 选中状态 Color
         * @return
         */
        @JvmStatic
        fun getCheckedStateList(context: Context, @ColorRes normalColor: Int, @ColorRes checkedColor: Int): ColorStateList =
            getColorStateList(context, normalColor, checkedColor, android.R.attr.state_checked)

        /**
         * 获取 mipmap 下的图片资源路径
         *
         * @param mipmapId 资源 ID
         */
        @JvmStatic
        fun getMipmapPath(context: Context, @DrawableRes mipmapId: Int): String =
            "android.resource://${context.packageName}/mipmap/$mipmapId"

        /**
         * 获取 drawable 下的图片资源路径
         *
         * @param drawableId 资源 ID
         */
        @JvmStatic
        fun getDrawablePath(context: Context, @DrawableRes drawableId: Int): String =
            "android.resource://${context.packageName}/drawable/$drawableId"
    }

}
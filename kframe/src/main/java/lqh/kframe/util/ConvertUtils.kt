package lqh.kframe.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue

/**
 * 功能：转换工具类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-10-15
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object ConvertUtils {

    /**
     * dp -> px
     */
    fun dp2px(dp: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        Resources.getSystem().displayMetrics
    )

    /**
     * px -> dp
     */
    fun px2dp(px: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        px,
        Resources.getSystem().displayMetrics
    )

    /**
     * sp -> px
     */
    fun sp2px(sp: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        Resources.getSystem().displayMetrics
    )

    /**
     * px -> sp
     */
    fun px2sp(px: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        px,
        Resources.getSystem().displayMetrics
    )

    /**
     * Bitmap -> Drawable
     */
    fun bitmap2Drawable(res: Resources, bitmap: Bitmap): Drawable = BitmapDrawable(res, bitmap)

    /**
     * Drawable -> Bitmap
     */
    fun drawable2Bitmap(drawable: Drawable) : Bitmap? {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}
package lqh.kframe.util

import android.content.res.Resources
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
}
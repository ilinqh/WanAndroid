package lqh.kframe.weight.bottom_tab

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import lqh.kframe.R

/**
 * 功能：底部 Tab
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/1
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class BottomTab {

    var tabName: Int = 0

    @ColorRes
    var tabNameColorNor: Int = 0

    @ColorRes
    var tabNameColorSel: Int = 0

    @DrawableRes
    var tabIconNor: Int = 0

    @DrawableRes
    var tabIconSel: Int = 0

    var tabFragment: Class<*>? = null

    var isSelect = false

    var showIcon = true

    var showText = true

    var view: View? = null

    @JvmOverloads
    constructor(
        @StringRes
        tabName: Int,
        @ColorRes
        tabNameColorNor: Int = R.color.color_tab_name_normal,
        @ColorRes
        tabNameColorSel: Int = R.color.color_tab_name_selected,
        @DrawableRes
        tabIconNor: Int,
        @DrawableRes
        tabIconSel: Int,
        tabFragment: Class<*>,
        isSel: Boolean = false
    ) {
        this.tabName = tabName
        this.tabNameColorNor = tabNameColorNor
        this.tabNameColorSel = tabNameColorSel
        this.tabIconNor = tabIconNor
        this.tabIconSel = tabIconSel
        this.tabFragment = tabFragment
        this.isSelect = isSel
    }

    constructor(view: View) {
        this.view = view
    }
}
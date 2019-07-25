package lqh.kframe.weight.statuslayout

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 * 功能：状态属性
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/25
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
data class StatusConfig(
    /**
     * 当前状态
     */
    var status: String,
    /**
     * 状态布局资源文件
     */
    @LayoutRes
    var layoutRes: Int = 0,
    /**
     * 状态布局 View
     */
    var view: View? = null,
    /**
     * 布局文件中需要点击的 资源ID
     */
    @IdRes
    var clickRes: Int = 0,
    var autoClick: Boolean = true
)
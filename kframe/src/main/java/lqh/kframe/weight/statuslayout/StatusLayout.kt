package lqh.kframe.weight.statuslayout

import android.content.Context
import android.widget.ViewAnimator
import androidx.annotation.LayoutRes

/**
 * 功能：状态布局
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/25
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class StatusLayout(context: Context) : ViewAnimator(context) {

    companion object {
        fun init(context: Context, @LayoutRes layoutId: Int): StatusLayout {
            return StatusLayout(context)
        }
    }

}
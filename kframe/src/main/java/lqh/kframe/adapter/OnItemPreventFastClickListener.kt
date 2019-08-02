package lqh.kframe.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import lqh.kframe.util.UIUtils

/**
 * 功能：BAVH 防止快速点击监听
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
interface OnItemPreventFastClickListener : BaseQuickAdapter.OnItemClickListener {

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (!UIUtils.isFastClick()) {
            onItemPreventFastClick(adapter, view, position)
        }
    }

    /**
     * 防止快速点击
     */
    fun onItemPreventFastClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int)

}
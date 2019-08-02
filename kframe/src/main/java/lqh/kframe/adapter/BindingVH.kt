package lqh.kframe.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

/**
 * 功能：基础 ViewHolder，存放视图缓存
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class BindingVH<Binding : ViewDataBinding>(view: View) : BaseViewHolder(view) {
    var binding: Binding? = null
}
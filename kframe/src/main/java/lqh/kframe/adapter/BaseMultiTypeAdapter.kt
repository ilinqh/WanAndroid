package lqh.kframe.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * 功能：多类型的 DataBinding 数据绑定 Adapter
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
abstract class BaseMultiTypeAdapter<T : MultiItemEntity, Binding : ViewDataBinding>(
    private val variableId: Int,
    dataList: List<T>
) : BaseMultiItemQuickAdapter<T, BindingVH<Binding>>(dataList) {

    override fun createBaseViewHolder(view: View): BindingVH<Binding> {
        return BindingVH(view)
    }

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): BindingVH<Binding> {
        return if (layoutResId != TYPE_NOT_FOUND) {
            val binding = DataBindingUtil.inflate<Binding>(mLayoutInflater, layoutResId, parent, false)
            val view = binding?.root ?: getItemView(layoutResId, parent)
            val holder = BindingVH<Binding>(view)
            holder.binding = binding
            holder
        } else {
            super.createBaseViewHolder(parent, layoutResId)
        }
    }

    override fun convert(helper: BindingVH<Binding>?, item: T) {
        helper?.binding?.let {
            it.setVariable(variableId, item)
            convert(helper, it, item)
            it.executePendingBindings()
        }
    }

    /**
     * 数据绑定到视图
     *
     * @param helper helper
     * @param binding 视图 Binding 实体
     * @param item    具体实体
     */
    protected abstract fun convert(helper: BindingVH<Binding>, binding: Binding, item: T)
}
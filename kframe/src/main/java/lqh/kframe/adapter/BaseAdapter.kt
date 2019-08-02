package lqh.kframe.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * 功能：封装了 DataBinding 数据绑定的 Adapter
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/2
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
abstract class BaseAdapter<T, Binding : ViewDataBinding>(
    private val variableId: Int,
    private val layoutId: Int,
    dataList: ArrayList<T>
) : BaseQuickAdapter<T, BindingVH<Binding>>(layoutId, dataList) {

    override fun createBaseViewHolder(view: View): BindingVH<Binding> {
        return BindingVH(view)
    }

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): BindingVH<Binding> {
        val binding = DataBindingUtil.inflate<Binding>(mLayoutInflater, layoutId, parent, false)
        val view = binding?.root ?: getItemView(layoutResId, parent)
        val holder = BindingVH<Binding>(view)
        holder.binding = binding
        return holder
    }

    override fun convert(helper: BindingVH<Binding>, item: T) {
        helper.binding?.let {
            it.setVariable(variableId, item)
            convert(it, item)
            it.executePendingBindings()
        }
    }

    /**
     * 数据绑定到视图
     *
     * @param binding 视图 Binding 实体
     * @param item    具体实体
     */
    protected abstract fun convert(binding: Binding, item: T)
}
package lqh.wanandroid.adapter

import lqh.kframe.adapter.BaseAdapter
import lqh.wanandroid.R
import lqh.wanandroid.databinding.ItemTestBinding

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class TestAdapter(data: ArrayList<Int>) : BaseAdapter<Int, ItemTestBinding>(0, R.layout.item_test, data) {

    override fun convert(binding: ItemTestBinding, item: Int) {
        binding.view.setBackgroundResource(item)
    }
}
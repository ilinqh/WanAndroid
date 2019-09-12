package lqh.wanandroid.fragment.mine

import android.view.View
import lqh.kframe.controller.BaseFragment
import lqh.kframe.weight.statuslayout.StatusLayout
import lqh.wanandroid.R
import lqh.wanandroid.databinding.FrgHomeBinding

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-02
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class MineFrg : BaseFragment<FrgHomeBinding>() {

    override fun initData() {
        statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)
    }

    override fun getLayoutId(): Int {
        return R.layout.frg_mine
    }

    override fun clickView(v: View) {
        when (v.id) {

        }
    }
}
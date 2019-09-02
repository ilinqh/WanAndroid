package lqh.wanandroid.activity

import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import lqh.kframe.controller.BaseAct
import lqh.kframe.weight.bottom_tab.BottomTab
import lqh.kframe.weight.statuslayout.StatusLayout
import lqh.wanandroid.R
import lqh.wanandroid.databinding.ActivityMainBinding
import lqh.wanandroid.fragment.HomeFrg
import lqh.wanandroid.fragment.MineFrg

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class MainActivity : BaseAct<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initData() {

        setSwipeBackEnable(false)

        statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)

        initTab()
    }

    /**
     * 初始化 tab
     */
    private fun initTab() {
        tabLayout.fragmentManager = supportFragmentManager
        val tabArrayList = arrayListOf<BottomTab>()
        // 首页
        val homeTab = BottomTab(
            tabName = R.string.str_tab_home,
            tabIconNor = R.drawable.icon_main_tab_home_normal,
            tabIconSel = R.drawable.icon_main_tab_home_selected,
            tabFragment = HomeFrg::class.java,
            isSel = true
        )
        // 我的
        val mineTab = BottomTab(
            tabName = R.string.str_tab_mine,
            tabIconNor = R.drawable.icon_main_tab_mine_normal,
            tabIconSel = R.drawable.icon_main_tab_mine_selected,
            tabFragment = MineFrg::class.java,
            isSel = false
        )
        tabArrayList.add(homeTab)
        tabArrayList.add(mineTab)

        tabLayout.setBottomTab(tabArrayList)
        tabLayout.showFragment(0)
    }

    override fun clickView(v: View) {
        when (v.id) {

        }
    }

}

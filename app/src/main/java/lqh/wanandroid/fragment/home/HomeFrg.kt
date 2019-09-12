package lqh.wanandroid.fragment.home

import android.view.View
import kotlinx.android.synthetic.main.frg_home.*
import lqh.kframe.controller.BaseFragment
import lqh.kframe.util.UIUtils
import lqh.kframe.weight.BannerView
import lqh.kframe.weight.statuslayout.StatusLayout
import lqh.wanandroid.R
import lqh.wanandroid.activity.common.WebAct
import lqh.wanandroid.databinding.FrgHomeBinding
import lqh.wanandroid.model.home.Banner
import lqh.wanandroid.mvp.contract.home.HomeContract
import lqh.wanandroid.mvp.presenter.home.HomePresenter

/**
 * 功能：首页
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-02
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class HomeFrg : BaseFragment<FrgHomeBinding>(), HomeContract.IView {

    private lateinit var homePresenter: HomePresenter

    override fun initData() {
        statusLayout.switchStatusLayout(StatusLayout.LOADING_STATUS)
        homePresenter = HomePresenter(mContext, this)
        homePresenter.getBanner()
    }

    override fun getLayoutId(): Int = R.layout.frg_home

    override fun getBannerSuccess(bannerList: ArrayList<Banner>?) {
        initBanner(bannerList)
        statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)
    }

    override fun getBannerFail(errorMsg: String) {
        bannerView.visibility = View.GONE
        statusLayout.switchStatusLayout(StatusLayout.ERROR_STATUS)
    }

    private fun initBanner(bannerList: java.util.ArrayList<Banner>?) {
        if (bannerList?.isNotEmpty() == true) {
            bannerView.clearData()
            bannerList.forEach {
                bannerView.addImageUrl(it.imagePath)
            }
            bannerView.commit()

            bannerView.itemClickListener = object : BannerView.OnBannerItemClickListener {
                override fun onBannerItemClickListener(position: Int) {
                    WebAct.startAct(mContext, bannerList[position].url)
                }

            }
            bannerView.visibility = View.VISIBLE
        } else {
            bannerView.visibility = View.GONE
        }
    }
}
package lqh.wanandroid.fragment.home

import android.os.Build
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.frg_home.*
import kotlinx.coroutines.launch
import lqh.kframe.controller.BaseFragment
import lqh.kframe.network.RetrofitFactory
import lqh.kframe.util.ImageUtils
import lqh.kframe.util.UIUtils
import lqh.kframe.weight.BannerView
import lqh.kframe.weight.statuslayout.StatusLayout
import lqh.wanandroid.R
import lqh.wanandroid.activity.common.WebAct
import lqh.wanandroid.coroutines.GitHubServiceApi
import lqh.wanandroid.databinding.FrgHomeBinding
import lqh.wanandroid.model.home.Banner
import lqh.wanandroid.mvp.contract.home.HomeContract
import lqh.wanandroid.mvp.presenter.home.HomePresenter
import lqh.wanandroid.network.HomeService

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.root.setPadding(0, UIUtils.getStatusBarHeight(), 0, 0)
        }
        statusLayout.switchStatusLayout(StatusLayout.LOADING_STATUS)
        homePresenter = HomePresenter(mContext, this)
        /*refreshLayout.setOnRefreshListener {
            onRefreshData()
        }*/

        onRefreshData()

        btnSearch.setOnClickListener {
            downImage()
        }
    }

    private fun downImage() {
        mainScope.launch {
            ImageUtils.downloadImage(
                mContext,
                "https://res.mall.ppxsc.com/goods/207/207_06245622405766468.jpg!j750"
            )
        }
    }

    override fun getLayoutId(): Int = R.layout.frg_home

    override fun onRefreshData() {
        mainScope.launch {
            val bannerList = getBanner()
            initBanner(bannerList)

//            refreshLayout.isRefreshing = false
            statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)
            return@launch
        }
//        homePresenter.getBanner()
        /*val bannerList =
            RetrofitFactory.getLiveDataRetroService(HomeService::class.java).bannerList()
        bannerList.observe(this, Observer {
            getBannerSuccess(it.data)
        })*/
    }

    private suspend fun getBanner(): ArrayList<Banner> {
        val retrofit = RetrofitFactory.getRetroService(GitHubServiceApi::class.java)
        val result = retrofit.getBanner()
        return result.data as ArrayList<Banner>
    }

    override fun getBannerSuccess(bannerList: ArrayList<Banner>?) {
        initBanner(bannerList)
//        refreshLayout.isRefreshing = false
        statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)
    }

    override fun getBannerFail(errorMsg: String) {
        bannerView.visibility = View.GONE
//        refreshLayout.isRefreshing = false
        statusLayout.switchStatusLayout(StatusLayout.ERROR_STATUS)
    }

    private fun initBanner(bannerList: ArrayList<Banner>?) {
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSearch -> {
                downImage()
            }
        }
    }
}
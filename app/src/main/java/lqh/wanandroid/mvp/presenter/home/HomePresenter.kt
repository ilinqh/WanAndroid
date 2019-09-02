package lqh.wanandroid.mvp.presenter.home

import android.content.Context
import io.reactivex.Observable
import lqh.kframe.network.BaseRequest
import lqh.kframe.network.HttpResponse
import lqh.wanandroid.model.home.Banner
import lqh.wanandroid.mvp.contract.home.HomeContract
import lqh.wanandroid.network.HomeService

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
class HomePresenter(var context: Context, var iView: HomeContract.IView) : HomeContract.IPresenter {

    override fun getBanner() {
        val request = BaseRequest<HomeService, ArrayList<Banner>>(context)
        request.request(HomeService::class.java, object : BaseRequest.RequestListener<ArrayList<Banner>> {
            override fun apply(params: Map<String, Any>): Observable<HttpResponse<ArrayList<Banner>>> {
                return request.retroService!!.getBanner()
            }

            override fun onRequestSuccess(bannerList: ArrayList<Banner>?) {
                iView.getBannerSuccess(bannerList)
            }

            override fun onRequestFailure(code: Int, message: String, data: ArrayList<Banner>?) {
                iView.getBannerFail(message)
            }
        })
    }
}
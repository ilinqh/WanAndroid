package lqh.wanandroid.mvp.contract.home

import lqh.wanandroid.model.home.Banner

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
class HomeContract {

    interface IView {

        /**
         * 获取 Banner 成功
         */
        fun getBannerSuccess(bannerList: ArrayList<Banner>?)

        /**
         * 获取 Banner 失败
         */
        fun getBannerFail(errorMsg: String)

    }

    interface IPresenter {
        /**
         * 获取 Banner
         */
        fun getBanner()
    }

}
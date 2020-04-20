package lqh.wanandroid.network

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.SingleSource
import lqh.kframe.network.HttpResponse
import lqh.wanandroid.model.home.Banner
import lqh.wanandroid.model.home.Chapter
import retrofit2.http.GET

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
interface HomeService {

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/chapters/json")
    fun getChapter(): Observable<HttpResponse<ArrayList<Chapter>>>

    /**
     * 获取 Banner
     */
    @GET("/banner/json")
    fun getBanner(): Observable<HttpResponse<ArrayList<Banner>>>

    @GET("/banner/json")
    fun bannerList(): LiveData<HttpResponse<ArrayList<Banner>>>

}
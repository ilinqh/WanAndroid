package lqh.wanandroid.coroutines

import lqh.kframe.network.HttpResponse
import lqh.wanandroid.model.home.Banner
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-24
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
interface GitHubServiceApi {
    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): User

    @GET("/banner/json")
    suspend fun getBanner() : HttpResponse<ArrayList<Banner>>
}
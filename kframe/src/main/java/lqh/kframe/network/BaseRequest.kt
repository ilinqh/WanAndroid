package lqh.kframe.network

import android.app.Activity

/**
 * 功能：发起网络请求的基类，泛型 T 是数据返回的实体类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/3
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class BaseRequest<T, E> {

    private val params by lazy {
        HashMap<String, Any>()
    }


    private val FILE_PART_KEY = "files"

    private val FILE_DESCRIPTION_KEY = "folders"

    var retroService: T? = null

    private lateinit var activity: Activity

    private var isShowDialog: Boolean = true

    private var limit: Long = 10

    /**
     * 没有网络
     */
    private val NO_NETWORK = 0

}
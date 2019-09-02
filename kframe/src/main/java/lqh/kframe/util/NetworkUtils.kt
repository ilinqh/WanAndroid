package lqh.kframe.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * 功能：获取网络状态的工具类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class NetworkUtils {

    companion object {
        /**
         * 判断当前是否有网络连接
         *
         * @param context context
         * @return 网络连接状态
         */
        fun isNetworkConnect(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            return networkInfo?.isAvailable == true
        }

        /**
         * 判断当前是否使用手机数据流量网络
         *
         * @param context context
         * @return isMobileConnect
         */
        fun isMobileConnect(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networks = manager.allNetworks
            for (network in networks) {

            }
            val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return false
        }
    }

}
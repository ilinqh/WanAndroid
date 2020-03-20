package lqh.kframe.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

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
object NetworkUtils {

    /**
     * 判断当前是否有网络连接
     *
     * @param context context
     * @return 网络连接状态
     */
    fun isNetworkConnect(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        manager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = it.getNetworkCapabilities(it.activeNetwork)
                networkCapabilities?.let { capabilities ->
                    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: let {
                    return false
                }
            } else {
                val networkInfo = it.activeNetworkInfo
                return networkInfo?.isConnected == true
            }
        } ?: let {
            return false
        }
    }


    /**
     * 判断当前是否使用手机数据流量网络
     *
     * @param context context
     * @return isMobileData
     */
    fun isMobileData(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        manager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = it.getNetworkCapabilities(it.activeNetwork)
                return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    ?: false
            } else {
                val networkInfo = it.activeNetworkInfo
//                return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                return networkInfo?.isConnected == true && networkInfo.type == ConnectivityManager.TYPE_MOBILE
            }
        } ?: let {
            return false
        }
    }
}
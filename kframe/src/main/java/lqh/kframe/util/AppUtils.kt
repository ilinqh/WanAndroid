package lqh.kframe.util

import android.app.ActivityManager
import android.content.Context

/**
 * 功能：获取 APP 相关信息工具类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-12
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object AppUtils {

    /**
     * 判断 APP 是否在前台显示
     *
     * @param context 上下文
     * @return true  - 显示于前台
     *         false - 隐藏于后台
     */
    fun appOnForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        appProcesses.forEach {
            if (it.processName == context.packageName &&
                it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            ) {
                return true
            }
        }
        return false
    }

    /**
     * 获取应用包名
     *
     * @param context 上下文
     * @return 应用名
     * */
    fun getApplicationName(context: Context): String? {
        return try {
            val packageManager = context.packageManager
            val applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)
            packageManager.getApplicationLabel(applicationInfo) as String
        } catch (e: Exception) {
            null
        }
    }
}
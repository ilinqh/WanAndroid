package lqh.wanandroid.config

import android.app.Application
import com.tencent.smtt.sdk.QbSdk
import lqh.kframe.network.RetrofitFactory

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/28
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        QbSdk.initX5Environment(this@MyApp, null)

        RetrofitFactory.INSTANCE.init(AppConfig.HOST, AppConfig.REQUEST_TIME_OUT, AppConfig.IS_PRO)
    }
}
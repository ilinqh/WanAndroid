package lqh.wanandroid

import android.app.Application
import com.tencent.smtt.sdk.QbSdk

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
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
    }
}
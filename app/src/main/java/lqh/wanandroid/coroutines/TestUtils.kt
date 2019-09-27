package lqh.wanandroid.coroutines

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import lqh.wanandroid.R
import lqh.wanandroid.activity.common.WebAct
import lqh.wanandroid.config.IntentKey

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-25
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object TestUtils {
    /**
     * 创建桌面快捷方式
     *
     * @param context
     */
    fun createShortCut(context: Context) {
        //创建Intent对象
        val shortcutIntent = Intent()

        //设置点击快捷方式，进入指定的Activity
        //注意：因为是从Lanucher中启动，所以这里用到了ComponentName
        //其中new ComponentName这里的第二个参数，是Activity的全路径名，也就是包名类名要写全。

        shortcutIntent.component = ComponentName(context.packageName, "lqh.wanandroid.activity.common.WebAct")

        //给Intent添加 对应的flag
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or Intent.FLAG_ACTIVITY_NEW_TASK)

        shortcutIntent.putExtra(IntentKey.KEY_URL, "http://www.baidu.com")

        val resultIntent = Intent()
        // Intent.ShortcutIconResource.fromContext 这个就是设置快捷方式的图标

        resultIntent.putExtra(
            Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
            Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher)
        )
        //启动的Intent
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)

        //这里可以设置快捷方式的名称
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "快捷名称")

        //设置Action
        resultIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"

        //发送广播、通知系统创建桌面快捷方式
        context.sendBroadcast(resultIntent)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun addShortcut(context: Context) {
        val shortcutManager = context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
        if(shortcutManager.isRequestPinShortcutSupported) {
            val shortcutInfoIntent = Intent(context, WebAct::class.java)
            shortcutInfoIntent.action = Intent.ACTION_VIEW
            shortcutInfoIntent.putExtra(IntentKey.KEY_URL, "http://www.baidu.com")
            val info = ShortcutInfo.Builder(context, "1")
                .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setShortLabel("Short Label")
                .setIntent(shortcutInfoIntent)
                .build()
            //当添加快捷方式的确认弹框弹出来时，将被回调
            val shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, Intent(), PendingIntent.FLAG_UPDATE_CURRENT)
//            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, MyReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT)
            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.intentSender)
        }
    }
}
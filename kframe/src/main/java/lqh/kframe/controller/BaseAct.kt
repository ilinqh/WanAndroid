package lqh.kframe.controller

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

/**
 * 功能：Activity 基类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
abstract class BaseAct : AppCompatActivity(), SwipeBackActivityBase, View.OnClickListener {

    private lateinit var mHelper: SwipeBackActivityHelper

    /**
     * 强制限制字体大小，避免字体大小随系统改变
     */
    override fun getResources(): Resources {
        var resources = super.getResources()
        val newConfig = resources.configuration
        val displayMetrics = resources.displayMetrics

        if (newConfig.fontScale != 1f) {
            newConfig.fontScale = 1f
            val configurationContext = createConfigurationContext(newConfig)
            resources = configurationContext.resources
            displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale
        }
        return resources
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout = mHelper.swipeBackLayout

    /**
     * 设置是否可滑动退出
     */
    override fun setSwipeBackEnable(enable: Boolean) = swipeBackLayout.setEnableGesture(enable)

    /**
     * 设置滑动退出的边缘起始位置
     *
     * @param flag 滑动边缘 SwipeBackLayout.EDGE_LEFT/EDGE_RIGHT/EDGE_BOTTOM/EDGE_ALL
     *             默认为 EDGE_LEFT
     */
    fun setOutEdge(flag: Int) = swipeBackLayout.setEdgeTrackingEnabled(flag)

    override fun scrollToFinishActivity() {
        Utils.convertActivityFromTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }

    fun addSwipeListener(listener: SwipeBackLayout.SwipeListener) = swipeBackLayout.addSwipeListener(listener)
}
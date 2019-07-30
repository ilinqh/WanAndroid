package lqh.kframe.controller

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import lqh.kframe.R
import lqh.kframe.util.KeyBoardUtils
import lqh.kframe.util.SystemUtils
import lqh.kframe.weight.statuslayout.StatusConfig
import lqh.kframe.weight.statuslayout.StatusLayout
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
abstract class BaseAct<T : ViewDataBinding> : AppCompatActivity(), SwipeBackActivityBase, View.OnClickListener,
    StatusLayout.OnLayoutClickListener {

    private lateinit var mHelper: SwipeBackActivityHelper

    protected lateinit var statusLayout: StatusLayout

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)

        statusLayout = StatusLayout.init(this, binding.root)
        // 添加不同状态
        addStatus()

        setContentView(statusLayout)
        statusLayout.onLayoutClickListener = this

        statusLayout.switchStatusLayout(StatusLayout.LOADING_STATUS)
        initData()
    }

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
     * 获取资源文件 ID
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化页面数据
     */
    protected abstract fun initData()

    /**
     * 添加不同状态
     */
    protected open fun addStatus() {
        // 加载中...
        statusLayout.addStatus(StatusConfig(StatusLayout.LOADING_STATUS, layoutRes = R.layout.status_layout_loading))
        // 出错
        statusLayout.addStatus(
            StatusConfig(
                StatusLayout.ERROR_STATUS,
                layoutRes = R.layout.status_layout_error,
                clickRes = R.id.layoutError
            )
        )
        // 无数据
        statusLayout.addStatus(StatusConfig(StatusLayout.EMPTY_STATUS, layoutRes = R.layout.status_layout_empty))
    }

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

    protected open fun addSwipeListener(listener: SwipeBackLayout.SwipeListener) =
        swipeBackLayout.addSwipeListener(listener)

    /**
     * 防止快速点击，在子 Activity 中处理点击事件时，不要重写这个方法，而应该重写
     * @see clickView(android.view.View) 方法
     */
    override fun onClick(v: View) {
        if (SystemUtils.isFastClick()) {
            return
        } else {
            clickView(v)
        }
    }

    override fun onLayoutClick(view: View, status: String) {
        when (view.id) {
            R.id.layoutError -> {
                // 出错
                statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)
            }
        }
    }

    /**
     * 在这个方法中做点击事件的处理
     */
    open fun clickView(v: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        KeyBoardUtils.hideKeyboard(this, statusLayout)
    }
}
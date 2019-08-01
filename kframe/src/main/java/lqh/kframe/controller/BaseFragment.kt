package lqh.kframe.controller

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import lqh.kframe.R
import lqh.kframe.util.KeyBoardUtils
import lqh.kframe.util.UIUtils
import lqh.kframe.weight.statuslayout.StatusConfig
import lqh.kframe.weight.statuslayout.StatusLayout

/**
 * 功能：基础 Fragment
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment(), View.OnClickListener,
    StatusLayout.OnLayoutClickListener {

    /**
     * 当前 Fragment 是否可见
     */
    protected var visible = true
    /**
     * 宿主Activity 是否已加载
     */
    private var isPrepared = false
    /**
     * 是否已加载结束
     */
    private var isLoad = false

    /**
     * 根视图
     */
    public lateinit var rootView: View

    /**
     * 数据绑定实体
     */
    protected lateinit var binding: T

    protected lateinit var statusLayout: StatusLayout

    /**
     * RecyclerView 数据加载 View
     */
    protected lateinit var loadingView: View

    /**
     * RecyclerView 没有数据显示 View
     */
    protected lateinit var emptyView: View

    /**
     * RecyclerView 加载出错显示 View
     */
    protected lateinit var errorView: View

    public lateinit var mContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mContext = binding.root.context
        statusLayout = StatusLayout.init(mContext, binding.root)
        addStatus()
        return statusLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            visible = true
            lazyLoad()
        } else {
            visible = false
        }
    }

    private fun lazyLoad() {
        if (!isPrepared || !visible || isLoad) {
            return
        }
        statusLayout.switchStatusLayout(StatusLayout.LOADING_STATUS)
        initData()
        isLoad = true
    }

    override fun onResume() {
        super.onResume()
        lazyLoad()
    }

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 获取当前 Fragment 布局文件资源 ID
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 刷新数据
     */
    protected open fun onRefreshData() {}

    /**
     * 添加不同状态
     */
    protected open fun addStatus() {
        // 加载中...
        statusLayout.addStatus(StatusConfig(StatusLayout.LOADING_STATUS, layoutRes = R.layout.layout_loading_status))
        // 出错
        statusLayout.addStatus(
            StatusConfig(
                StatusLayout.ERROR_STATUS,
                layoutRes = R.layout.layout_error_status,
                clickRes = R.id.layoutError
            )
        )
        // 无数据
        statusLayout.addStatus(StatusConfig(StatusLayout.EMPTY_STATUS, layoutRes = R.layout.layout_empty_status))
    }

    /**
     * 若 Fragment 中有 RecyclerView 同时需要切换不同状态，一定要先调用此方法初始化各个视图
     */
    protected fun initBaseRecyclerView(recyclerView: RecyclerView) {
        val parent = recyclerView.parent as ViewGroup
        // 加载中
        loadingView = layoutInflater.inflate(R.layout.layout_loading_status, parent, false)
        // 出错了
        errorView = layoutInflater.inflate(R.layout.layout_error_status, parent, false)
        errorView.setOnClickListener {
            onRefreshData()
        }
        // 空数据
        emptyView = layoutInflater.inflate(R.layout.layout_empty_status, parent, false)
        emptyView.setOnClickListener {
            onRefreshData()
        }
    }

    /**
     * 防止快速点击，在子 Activity 中处理点击事件时，不要重写这个方法，而应该重写
     * @see clickView(android.view.View) 方法
     */
    override fun onClick(v: View) {
        if (UIUtils.isFastClick()) {
            return
        } else {
            clickView(v)
        }
    }

    /**
     * 在这个方法中做点击事件的处理
     */
    open fun clickView(v: View) {}

    override fun onLayoutClick(view: View, status: String) {
        when (view.id) {
            R.id.layoutError -> {
                // 出错
                statusLayout.switchStatusLayout(StatusLayout.LOADING_STATUS)
                onRefreshData()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        KeyBoardUtils.hideKeyboard(mContext, statusLayout)
    }
}
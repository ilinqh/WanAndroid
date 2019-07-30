package lqh.kframe.weight.statuslayout

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ViewAnimator
import androidx.annotation.LayoutRes
import lqh.kframe.R

/**
 * 功能：状态布局
 *
 * 可直接在基类 Activity/Fragment 中调用 init() 将 layoutId 传入后将生成的 StatusLayout 当做根布局
 * 也可以在 XML 中直接使用，但布局中只能有一个子控件，有多个子控件时，用法类似 ScrollView。
 *
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/25
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class StatusLayout(context: Context, attrs: AttributeSet? = null) : ViewAnimator(context, attrs) {

    companion object {

        /**
         * 默认状态
         */
        const val NORMAL_STATUS = "NORMAL_STATUS"

        /**
         * 加载状态
         */
        const val LOADING_STATUS = "LOADING_STATUS"

        /**
         * 错误状态
         */
        const val ERROR_STATUS = "ERROR_STATUS"

        /**
         * 空状态
         */
        const val EMPTY_STATUS = "EMPTY_STATUS"

        var animIn: Int = R.anim.anim_in_alpha
        var animOut: Int = R.anim.anim_out_alpha

        /**
         * 用于 activity/fragment 等 view 的初始化方式使用
         * 在布局文件中可以不用手动把根部局替换成 StatusLayout
         * 而是调用 init 方法把资源 layoutId 传进来，返回一个 StatusLayout，
         * 把返回的 StatusLayout 作为 setContentView(view) 方法的参数
         */
        fun init(context: Context, @LayoutRes layoutId: Int): StatusLayout {
            val rootView = LayoutInflater.from(context).inflate(layoutId, null)
                ?: throw NullPointerException("View can not be null.")
            return init(context, rootView)
        }

        fun init(context: Context, view: View): StatusLayout {
            val statusLayout = StatusLayout(context)
            statusLayout.addStatus(StatusConfig(NORMAL_STATUS, view = view))
            return statusLayout
        }
    }

    private val mLayoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    /**
     * 当前显示的 view 在 ViewGroup 中的位置
     */
    var currentPosition = 0

    var onLayoutClickListener: OnLayoutClickListener? = null

    /**
     * 添加状态
     */
    fun addStatus(config: StatusConfig): StatusLayout {
        var (status, layoutRes, view, clickRes) = config
        if (view == null) {
            view = mLayoutInflater.inflate(layoutRes, this, false)
        }
        /**
         * 传入了 clickRes 时再监听，否则交给 view 自身去处理逻辑
         * 并且判断是否需要自动处理点击事件
         * autoClick 为 false 时跳过点击事件相关处理
         */
        val clickView: View? = view?.findViewById(clickRes)
        clickView?.setOnClickListener {
            onLayoutClickListener?.onLayoutClick(it, status)
        }

        val index = getViewIndexByStatus(status)
        view?.let {
            it.tag = status
            // 如果同个 status 已被添加，则先移除之前的
            if (index >= 0) {
                removeViewAt(index)
                addView(it, index)
            } else {
                addView(it)
            }
        }

        return this
    }

    /**
     * 根据 status 的值来切换相对应类型的布局
     *
     * @param status 布局状态
     */
    fun switchStatusLayout(status: String) {
        if (inAnimation == null || outAnimation == null) {
            setInAnimation(context, animIn)
            setOutAnimation(context, animOut)
        }

        val index = getViewIndexByStatus(status)
        if (index >= 0 && currentPosition != index) {
            displayedChild = index
            currentPosition = index
        }
    }

    /**
     * 通过 status 来找出对应 status 的 key
     * key 存放的就该 status 的 view 在 viewGroup 中的 index
     */
    private fun getViewIndexByStatus(status: String): Int {
        for (position in 0 until childCount) {
            if (getChildAt(position).tag != null && TextUtils.equals(getChildAt(position).tag as String, status)) {
                return position
            }
        }
        return -1
    }

    interface OnLayoutClickListener {
        fun onLayoutClick(view: View, status: String)
    }

}
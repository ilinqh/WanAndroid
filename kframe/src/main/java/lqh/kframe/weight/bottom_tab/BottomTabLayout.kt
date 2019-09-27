package lqh.kframe.weight.bottom_tab

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import lqh.kframe.R
import lqh.kframe.util.ResUtils
import lqh.kframe.util.dp2px

/**
 * 功能：底部 bottom tab 布局，请不要在 XML 代码中添加布局，添加的布局会在初始化时被移除
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/8/1
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */

class BottomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    /**
     * 用于存放 Fragment
     */
    private val flContainer: FrameLayout by lazy {
        FrameLayout(context)
    }

    /**
     * Fragment 和底部 tab 分割线
     */
    private val cuttingLine: View by lazy {
        View(context)
    }

    /**
     * 存放底部 tab
     */
    private val tabHost: LinearLayout by lazy {
        LinearLayout(context)
    }

    /**
     * 用于存放用户传进来的 BottomTab 对象列表
     */
    private val tabList by lazy {
        ArrayList<BottomTab>()
    }

    /**
     * 内部真正使用的 tab 视图列表
     */
    private val tabViewList by lazy {
        ArrayList<View>()
    }

    /**
     * 当前显示的 fragment
     */
    private var currentFragment: Fragment? = null

    private val frgList by lazy {
        SparseArray<Fragment>()
    }

    var onTabClickListener: OnBottomTabClickListener? = null

    var fragmentManager: FragmentManager? = null

    init {
        /**
         * 先移除所有 View
         */
        removeAllViews()
        orientation = VERTICAL
        clipChildren = false

        flContainer.apply {
            id = R.id.flContainer
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)
        }

        cuttingLine.apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 1.dp2px())
            setBackgroundColor(Color.parseColor("#DCDCDC"))
        }

        tabHost.apply {
            gravity = Gravity.CENTER
            orientation = HORIZONTAL
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 49.dp2px())
        }

        addView(flContainer)
        addView(cuttingLine)
        addView(tabHost)
    }

    /**
     * 用于设置数据，一定要调用，不调用则没有效果
     */
    fun setBottomTab(bottomTabList: ArrayList<BottomTab>) {
        tabList.clear()
        tabList.addAll(bottomTabList)
        for (i in tabList.indices) {
            val view = initTabView(i)
            tabHost.addView(view)
        }
    }

    private fun initTabView(index: Int): View {
        val tab = tabList[index]
        val view = tab.view ?: LayoutInflater.from(context).inflate(
            R.layout.item_bottom_tab,
            tabHost,
            false
        )
        if (tab.view == null) {
            val tabImage = view.findViewById<ImageView>(R.id.tabImage)
            val tabText = view.findViewById<TextView>(R.id.tabText)
            tabImage.apply {
                visibility = if (tab.showIcon) {
                    setImageResource(
                        if (tab.isSelect) {
                            tab.tabIconSel
                        } else {
                            tab.tabIconNor
                        }
                    )
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
            tabText.apply {
                visibility = if (tab.showText) {
                    setText(tab.tabName)
                    setTextColor(
                        ResUtils.getColor(
                            context,
                            if (tab.isSelect) {
                                tab.tabNameColorSel
                            } else {
                                tab.tabNameColorNor
                            }
                        )
                    )
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
        tabViewList.add(view)

        tab.tabFragment?.let {
            val fragment = Fragment.instantiate(context, it.name)
            frgList.put(index, fragment)
        }
        view.setOnClickListener(OnTabClickListener(index))

        return view
    }

    inner class OnTabClickListener(private val index: Int) : OnClickListener {

        override fun onClick(v: View) {
            val tabView = tabViewList[index]
            val tab = tabList[index]

            if (tab.view == null) {
                // 清除所有 tab 的选中状态
                clearAllSelect()
                val imageView = tabView.findViewById<ImageView>(R.id.tabImage)
                val textView = tabView.findViewById<TextView>(R.id.tabText)

                imageView.setImageResource(tab.tabIconSel)
                textView.setTextColor(ResUtils.getColor(context, tab.tabNameColorSel))
            }

            showFragment(index)

            onTabClickListener?.onTabClick(index)
        }
    }

    /**
     * 将所有 Tab 设置为未选中状态
     */
    fun clearAllSelect() {
        for (position in tabViewList.indices) {
            val bottomTab = tabList[position]
            if (bottomTab.view == null) {
                val tabView = tabViewList[position]
                val imageView = tabView.findViewById<ImageView>(R.id.tabImage)
                val textView = tabView.findViewById<TextView>(R.id.tabText)

                if (bottomTab.showIcon) {
                    imageView.setImageResource(tabList[position].tabIconNor)
                }

                if (bottomTab.showText) {
                    textView.setTextColor(
                        ResUtils.getColor(
                            context,
                            tabList[position].tabNameColorNor
                        )
                    )
                }
            }
        }
    }

    /**
     * 显示图标上的提示点
     */
    @Throws(Exception::class)
    fun showTipPoint(bottomTab: BottomTab) {
        if (bottomTab.view == null) {
            val index = tabList.indexOf(bottomTab)
            val view = tabViewList[index]
            val layoutTipPoint = view.findViewById<View>(R.id.tipPoint)
            layoutTipPoint.visibility = View.VISIBLE
        } else {
            throw Exception("the external view must show tip point by external")
        }
    }


    /**
     * 显示图标上的提示点
     */
    @Throws(Exception::class)
    fun showTipPointByName(vararg tabNameList: Int) {
        for (bottomTab in tabList) {
            for (tabName in tabNameList) {
                if (bottomTab.tabName == tabName) {
                    showTipPoint(bottomTab)
                }
            }
        }
    }

    /**
     * 隐藏图标上的提示点
     */
    @Throws(Exception::class)
    fun hideTipPoint(bottomTab: BottomTab) {
        if (bottomTab.view == null) {
            val index = tabList.indexOf(bottomTab)
            val view = tabViewList[index]
            val layoutTipPoint = view.findViewById<View>(R.id.tipPoint)
            layoutTipPoint.visibility = View.GONE
        } else {
            throw Exception("the external view must hide tip point by external")
        }
    }

    /**
     * 设置显示的 Fragment
     *
     * @param index
     */
    fun showFragment(index: Int) {
        val tab = tabList[index]
        tab.tabFragment?.let {
            val fragment = frgList.get(index)
            if (fragment != null && currentFragment != fragment) {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.let { ft ->
                    currentFragment?.let { curFrg ->
                        if (!curFrg.isHidden) {
                            ft.hide(curFrg)
                        }
                    }
                    if (!fragment.isAdded) {
                        ft.add(R.id.flContainer, fragment)
                    }
                    ft.show(fragment)
                    ft.commit()
                }
                currentFragment = fragment
            }
        }
    }

    fun hideFragment(fragment: Fragment) {
        fragment.fragmentManager?.beginTransaction()?.hide(fragment)?.commitAllowingStateLoss()
        fragment.userVisibleHint = false
    }

    /**
     * bottomTab 点击后回调接口
     */
    interface OnBottomTabClickListener {
        fun onTabClick(index: Int)
    }
}
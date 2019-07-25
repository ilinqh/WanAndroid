package lqh.kframe.weight

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_banner_view.view.*
import kotlinx.android.synthetic.main.layout_banner_view_item.view.*
import lqh.kframe.R
import lqh.kframe.util.dp2px

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class BannerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "BannerView"

        /**
         * 如果少于 LAST_ITEM 张则不自动播放
         */
        const val LEAST_ITEM = 2

        /**
         * 处于停止自动播放状态后，每隔一段时间检查一次是否自动播放
         */
        const val CHECK_TIME: Long = 5000
    }

    private var contentView = LayoutInflater.from(context).inflate(R.layout.layout_banner_view, this, true)

    /**
     * banner 数量
     */
    private var count: Int = 0

    /**
     * 存放 banner
     */
    private lateinit var viewList: ArrayList<View>

    /**
     * 是否自动播放
     */
    private var isAutoPlay = false

    private var mHandler = Handler()

    /**
     * 当前位置
     */
    private var currentItem = 0

    /**
     * 用于记录指示器指示位置
     */
    private var isChecked = SparseBooleanArray()

    /**
     * banner 展示图片的 URL
     */
    private var bannerUrlList: ArrayList<String> = ArrayList()

    /**
     * 指示器宽度
     */
    var dotWith = 5.dp2px()
        set(value) {
            field = value.dp2px()
        }

    /**
     * 指示器高度
     */
    var dotHeight = 5.dp2px()
        set(value) {
            field = value.dp2px()
        }

    /**
     * 指示器间距
     */
    var dotSpace = 10.dp2px()
        set(value) {
            field = value.dp2px()
        }

    /**
     * banner 展示时间
     */
    var delay: Long = 4000

    /**
     * 非选中状态的指示点透明度
     */
    var alpha = 153

    /**
     * banner 适配器
     */
    var adapter: BannerAdapter
    /**
     * banner 点击事件
     */
    var listener: OnBannerItemClickListener? = null

    /**
     * 图片显示配置
     */
    var imageLp: LinearLayout.LayoutParams? = null

    /**
     * 指示器显示配置
     */
    var dotLp: RelativeLayout.LayoutParams? = null

    /**
     * 指示器图片
     */
    var dotImage = R.drawable.shape_indicator_background

    /**
     * banner 图片圆角角度
     */
    var roundRadius = 0F

    private var task = object : Runnable {
        override fun run() {
            if (isAutoPlay) {
                // 位置循环
                currentItem = currentItem % (count + 1) + 1
                // 正常每隔一段时间播放一张图片
                viewPager.currentItem = currentItem
                mHandler.postDelayed(this, delay)
            } else {
                // 如果处于拖拽状态停止自动播放，每隔 CHECK_TIME 毫秒检查一次是否可以正常自动播放
                mHandler.postDelayed(this, CHECK_TIME)
            }
        }
    }

    init {
        adapter = BannerAdapter()
        viewPager.adapter = adapter
    }

    /**
     * 添加图片
     */
    fun addImageUrl(url: String) {
        bannerUrlList.add(url)
    }

    private fun setViewPager(list: ArrayList<String>) {
        // 设置 Banner 图
        setViewList(list)

        // 从第 1 张图片开始(位置刚好也是 1，注意：0 位置是最后一张图片)
        currentItem = 1
        viewPager.currentItem = currentItem
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0..llDot.childCount) {
                    val child = llDot.getChildAt(i)
                    child.setBackgroundResource(dotImage)
                    if (i == position - 1) {
                        // 被选中
                        child.background.alpha = 255
                        if (!isChecked[i]) {
                            isChecked.put(i, true)
                        }
                    } else {
                        // 未被选中
                        child.background.alpha = alpha
                        if (isChecked[i]) {
                            isChecked.put(i, false)
                        }
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_IDLE -> {
                        // 闲置状态
                        if (viewPager.currentItem == 0) {
                            viewPager.setCurrentItem(count, false)
                        } else if (viewPager.currentItem == count + 1) {
                            viewPager.setCurrentItem(1, false)
                        }
                        currentItem = viewPager.currentItem
                        isAutoPlay = true
                    }
                    ViewPager.SCROLL_STATE_DRAGGING -> {
                        // 拖拽中
                        isAutoPlay = false
                    }
                    ViewPager.SCROLL_STATE_SETTLING -> {
                        // 设置中
                        isAutoPlay = false
                    }
                }
            }
        })
    }

    /**
     * 设置 Banner 图
     */
    private fun setViewList(list: ArrayList<String>) {
        viewList = ArrayList()
        for (i in 0..count + LEAST_ITEM) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_banner_view_item, viewPager, false)
            if (imageLp != null) {
                imageView.layoutParams = imageLp
            }
            val url: String = when (i) {
                0 -> // 将 viewPager 的初始页设置为数据列表的最后一项
                    list[count - 1]
                count + 1 -> // viewpager 最后一页设置为数据列表的第一项
                    list[0]
                else -> list[i - 1]
            }
            Glide.with(context)
                .load(url)
                .error(R.drawable.icon_pic_fail)
                .into(imageView)

            imageView.radius = roundRadius

            viewList.add(view)
        }
    }

    /**
     * 设置指示器
     */
    private fun setIndicator() {
        isChecked.clear()
        llDot.removeAllViews()
        for (i in 0..count) {
            val view = View(context)
            view.setBackgroundResource(dotImage)
            view.background.alpha = alpha
            val params = LinearLayout.LayoutParams(dotWith, dotHeight)
            params.leftMargin = dotSpace / 2
            params.topMargin = dotSpace / 2
            params.rightMargin = dotSpace / 2
            params.bottomMargin = dotSpace / 2
            llDot.addView(view, params)
            isChecked.put(i, false)
        }
        llDot.getChildAt(0).background.alpha = 255
        isChecked.put(0, true)
    }

    /**
     * 开始轮播
     */
    private fun startPlay() {
        if (count < LEAST_ITEM) {
            isAutoPlay = false
        } else {
            isAutoPlay = true
            handler.postDelayed(task, delay)
        }
    }

    /**
     * 设置完 BannerView 后提交操作
     *
     * !!!!必须步骤，非常重要!!!!
     */
    fun commit() {
        count = bannerUrlList.size
        // 设置 ViewPager
        setViewPager(bannerUrlList)
        // 设置索引
        currentItem = 1
        // 设置指示器
        setIndicator()
        // 开始轮播
        startPlay()
    }

    /**
     * 重置 banner 数据
     */
    fun clearData() {
        viewList.clear()
        isChecked.clear()
        bannerUrlList.clear()
        handler.removeCallbacks(task)
    }

    inner class BannerAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View, any: Any): Boolean {
            return view === any
        }

        override fun getCount(): Int = viewList.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = viewList[position]
            view.setOnClickListener {
                listener?.onBannerItemClickListener(position - 1)
            }
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
            container.removeView(viewList[position])
        }
    }

    interface OnBannerItemClickListener {
        /**
         * banner 点击事件
         */
        fun onBannerItemClickListener(position: Int)
    }

}
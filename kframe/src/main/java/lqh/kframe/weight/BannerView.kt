package lqh.kframe.weight

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import lqh.kframe.R

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
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
        const val LAST_ITEM = 2
    }

    private var mContext: Context = context

    private lateinit var contentView: View

    private lateinit var viewPager: ViewPager

    private lateinit var llDot: LinearLayout

    /**
     * banner 数量
     */
    private var count: Int = 0

    /**
     * 存放 banner
     */
    private lateinit var viewList: List<View>

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
    private var bannerUrlList = ArrayList<String>()

    /**
     * 指示器宽度
     */
//    private var dotWith = 5.Scrdp2px()

    /**
     * 指示器高度
     */
//    private lateinit var dotHeight: Int

    init {
        // 初始化 View
        initView()
        // 初始化数据
        initData()
    }

    private fun initView() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_banner_view, this, true)

    }

    private fun initData() {

    }

}
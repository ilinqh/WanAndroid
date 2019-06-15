package lqh.kframe.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import lqh.kframe.R

/**
 * 功能：圆角 ImageView
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class RoundImageView : AppCompatImageView {

    companion object {
        // 默认圆角角度
        private const val DEFAULT_RADIUS = 8f
    }

    private var mPath: Path
    private lateinit var mRectF: RectF
    private val rids = FloatArray(8)
    private var paintFlagsDrawFilter: PaintFlagsDrawFilter

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        val mRadius = array.getDimension(R.styleable.RoundImageView_radius, DEFAULT_RADIUS)
        rids[0] = mRadius
        rids[1] = mRadius
        rids[2] = mRadius
        rids[3] = mRadius
        rids[4] = mRadius
        rids[5] = mRadius
        rids[6] = mRadius
        rids[7] = mRadius
        array.recycle()

        mPath = Path()
        paintFlagsDrawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas?) {
        mPath.reset()
        mPath.addRoundRect(mRectF, rids, Path.Direction.CW)
        canvas?.drawFilter = paintFlagsDrawFilter
        canvas?.save()
        canvas?.clipPath(mPath)
        super.onDraw(canvas)
        canvas?.restore()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
    }
}
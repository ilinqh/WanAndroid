package lqh.kframe.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import lqh.kframe.R
import lqh.kframe.util.dp2px

/**
 * 功能：圆角 ImageView
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/16
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        // 默认圆角角度
        private val DEFAULT_RADIUS = 4.dp2px().toFloat()
    }

    private var mPath: Path
    private lateinit var mRectF: RectF
    private val rids = FloatArray(8)
    private var paintFlagsDrawFilter: PaintFlagsDrawFilter

    /**
     * 圆角角度
     */
    var radius = 0F
        set(value) {
            field = value
            rids[0] = field
            rids[1] = field
            rids[2] = field
            rids[3] = field
            rids[4] = field
            rids[5] = field
            rids[6] = field
            rids[7] = field
            invalidate()
        }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        radius = array.getDimension(R.styleable.RoundImageView_radius, DEFAULT_RADIUS)
        array.recycle()
        mPath = Path()
        paintFlagsDrawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        mPath.reset()
        mPath.addRoundRect(mRectF, rids, Path.Direction.CW)
        canvas.apply {
            drawFilter = paintFlagsDrawFilter
            save()
            clipPath(mPath)
        }
        super.onDraw(canvas)
        canvas.restore()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
    }


}
package lqh.kframe.util.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2020/3/20
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class GridDividerItemDecoration(
    private val layoutManager: GridLayoutManager
) : RecyclerView.ItemDecoration() {

    private val spanCount = layoutManager.spanCount

    private val spanSizeLookup = layoutManager.spanSizeLookup

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // TODO: 2020/3/20 尝试删除 super.onDraw() 看效果
        super.onDraw(c, parent, state)
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // TODO: 2020/3/20 尝试删除 super.onDrawOver() 看效果
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

    }
}
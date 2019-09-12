package lqh.kframe.util

import android.content.Context
import android.widget.Toast

/**
 * 功能：提示工具类，同一间间段内，只会有一个 Toast 弹出
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-08-30
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object TipUtils {
    private var toast: Toast? = null

    fun showToast(context: Context, content: String, duration: Int = Toast.LENGTH_SHORT) {
        if (null == toast) {
            toast = Toast.makeText(context, content, duration)
        } else {
            toast!!.setText(content)
        }
        toast?.show()
    }
}
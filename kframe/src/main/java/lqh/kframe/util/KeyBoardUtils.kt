package lqh.kframe.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 功能：键盘工具类，控制键盘的弹出和收回
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/29
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object KeyBoardUtils {

    /**
     * 显示 KeyBoard
     */
    fun showKeyBoard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
    }

    /**
     * view 为接受软键盘输入的视图，SHOW_FORCED 表示强制显示
     */
    fun showKeyBoard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 隐藏 KeyBoard
     */
    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 获取 KeyBoard 打开的状态
     *
     * @return 若返回true，则表示输入法打开
     */
    fun isOpen(context: Context): Boolean {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.isActive
    }

}
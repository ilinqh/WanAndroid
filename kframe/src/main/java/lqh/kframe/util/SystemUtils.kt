package lqh.kframe.util

/**
 * 功能：系统工具
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/7/29
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class SystemUtils {
    companion object {

        private const val FAST_CLICK_DELAY_TIME = 500

        private var lastClickTime = 0L

        /**
         * 判断是否快速点击
         */
        fun isFastClick(): Boolean {
            var flag = false
            val currentClickTime = System.currentTimeMillis()
            if (currentClickTime - lastClickTime < FAST_CLICK_DELAY_TIME) {
                flag = true
            }
            lastClickTime = currentClickTime
            return flag
        }
    }
}
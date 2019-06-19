package lqh.kframe.util.rxbus

import io.reactivex.functions.Consumer

/**
 * 功能：自定义 Consumer，避免发生异常后执行 onError 事件导致终止事件订阅
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
abstract class BaseConsumer<T> : Consumer<T> {

    override fun accept(t: T) {
        try {
            consumer(t)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun consumer(t: T)
}
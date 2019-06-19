package lqh.kframe.util.rxbus

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import java.util.concurrent.ConcurrentHashMap

/**
 * 功能：事件总线，支持事件总线基础功能，粘性事件，发生异常后不终止订阅
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author 林钦宏
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/19
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class RxBus private constructor() {

    // 使用 toSerialized 的好处是：在多线程的情况下，可以保证线程安全。
    private val bus: Relay<Any> = PublishRelay.create<Any>().toSerialized()
    // ConcurrentHashMap 是一个线程安全的 HashMap，采用stripping lock（分离锁），效率比HashTable高很多。
    private val mStickEventMap: ConcurrentHashMap<Class<*>, Any> = ConcurrentHashMap()

    companion object {
        @JvmStatic
        fun getInstance(): RxBus = Holder.BUS
    }

    fun post(event: Any) = bus.accept(event)

    fun postSticky(event: Any) {
        synchronized(mStickEventMap) {
            mStickEventMap[event.javaClass] = event
        }
        post(event)
    }

    fun <T> toObservable(eventType: Class<T>): Observable<T> = bus.ofType(eventType)

    fun <T> toObservableSticky(eventType: Class<T>): Observable<T> {
        synchronized(mStickEventMap) {
            val observable = bus.ofType(eventType)
            val event = mStickEventMap[eventType]
            return if (event != null) {
                observable.mergeWith(Observable.create {
                    it.onNext(eventType.cast(event))
                })
            } else {
                observable
            }
        }
    }

    fun hasObservers(): Boolean = bus.hasObservers()

    @JvmOverloads
    fun <T> register(
        eventType: Class<T>,
        onNext: BaseConsumer<T>,
        scheduler: Scheduler? = AndroidSchedulers.mainThread(),
        onError: Consumer<in Throwable>? = Functions.ERROR_CONSUMER,
        onComplete: Action? = Functions.EMPTY_ACTION,
        onSubscribe: Consumer<in Disposable>? = Functions.emptyConsumer()
    ): Disposable = toObservable(eventType)
        .observeOn(scheduler)
        .subscribe(onNext, onError, onComplete, onSubscribe)

    /**
     * 移除指定 eventType 的 Sticky 事件
     *
     * @param eventType
     * @param <T>
     * @return
     */
    fun <T> removeStickyEvent(eventType: Class<T>): T? {
        synchronized(mStickEventMap) {
            return eventType.cast(mStickEventMap.remove(eventType))
        }
    }

    /**
     * 移除所有的 Sticky 事件
     */
    fun removeAllStickyEvents() {
        synchronized(mStickEventMap) {
            mStickEventMap.clear()
        }
    }

    fun unregister(disposable: Disposable?) {
        if (disposable?.isDisposed == true) {
            disposable.dispose()
        }
    }

    private class Holder {
        companion object {
            val BUS = RxBus()
        }
    }
}
package com.hkllzh.gank.util

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject


class RxBus private constructor() {
    // 主题
    private val mBus: Subject<Any, Any> = SerializedSubject(PublishSubject.create<Any>())

    // 提供了一个新的事件
    fun post(o: Any) {
        mBus.onNext(o)
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return mBus.ofType(eventType)
    }

    companion object {
        fun getDefault(): RxBus {
            return RxBusHolder.sInstance
        }

        class RxBusHolder {
            companion object {
                val sInstance = RxBus()
            }
        }
    }


}
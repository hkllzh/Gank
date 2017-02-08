package com.hkllzh.gank.study

import android.content.Context
import android.util.Log
import rx.Observable
import rx.Observer
import rx.Subscriber


/**
 * Created by lizheng on 2017/2/8.
 */
class StudyRxJava {

    private val TAG = "StudyRxJava"

    private var mContext: Context? = null
    fun demo(context: Context) {
        mContext = context

        demo1();
        demo2();
    }

    private fun demo2() {

        Observable.create(Observable.OnSubscribe<String> { s ->
            s.onNext("hello 2")
            s.onNext("world 2")
        }).subscribe(object : Subscriber<String>() {
            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError")
            }

            override fun onNext(t: String?) {
                Log.d(TAG, "onNext -$t-")
            }
        })
    }

    private fun demo1() {
        // 1. 观察者1 实现一个接口
        val observer1 = object : Observer<String> {
            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError")
            }

            override fun onNext(t: String?) {
                Log.d(TAG, "onNext -$t-")
            }

            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }
        }

        // 2. 观察者2 实现一个抽象类，功能更多一些
        val observer2 = object : Subscriber<String>() {
            override fun onError(e: Throwable?) {

            }

            override fun onNext(t: String?) {

            }

            override fun onCompleted() {

            }
        }

        // 3. 被观察者
        val observable = Observable.create(Observable.OnSubscribe<String> { subscriber ->
            subscriber.onNext("Hello 1")
            subscriber.onNext("World 1")
            subscriber.onCompleted()
        })

        observable.subscribe(observer1)
    }

}
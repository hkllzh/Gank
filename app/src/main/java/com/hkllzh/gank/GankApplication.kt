package com.hkllzh.gank

import android.app.Application
import io.realm.Realm

/**
 * Created by lizheng on 2017/2/10.
 */
class GankApplication : Application() {

    override fun onCreate() {
        dbInit()
    }

    fun dbInit() {
        Realm.init(this)
    }
}
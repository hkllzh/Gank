package com.hkllzh.gank

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import io.realm.Realm

/**
 * Created by lizheng on 2017/2/10.
 */
class GankApplication : Application() {

    override fun onCreate() {
        dbInit()
        picInit()
    }

    private fun picInit() {
        Fresco.initialize(this)
    }

    private fun dbInit() {
        Realm.init(this)
    }
}
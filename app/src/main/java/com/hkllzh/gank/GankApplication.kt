package com.hkllzh.gank

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.hkllzh.gank.net.APIManager
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
        val config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, APIManager.getOkHttpClient())
                .build();
        Fresco.initialize(this, config);
    }

    private fun dbInit() {
        Realm.init(this)
    }
}
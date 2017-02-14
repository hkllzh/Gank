package com.hkllzh.gank.db

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by lizheng on 2017/2/14.
 */
abstract open class BaseDB {
    protected var mRealm: Realm? = null
    private fun _init() {
        if (mRealm != null) {
            return
        }
        val defaultConfig = RealmConfiguration.Builder()
                .name(getDBName())
                .schemaVersion(getDBVersion())
                .build()
        mRealm = Realm.getInstance(defaultConfig)
    }

    private fun _close() {
        if (null == mRealm) {
            return
        }

        mRealm!!.close()
        mRealm = null
    }

    fun _action(_a: () -> Unit) {
        _init()
        _a()
        _close()
    }

    protected abstract fun getDBName(): String
    protected abstract fun getDBVersion(): Long

}
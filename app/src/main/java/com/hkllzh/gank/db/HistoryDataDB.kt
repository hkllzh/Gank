package com.hkllzh.gank.db

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import java.util.*

/**
 * Created by lizheng on 2017/2/10.
 */
class HistoryDataDB private constructor() {
    private val debug = true
    private val TAG = "HistoryDataDB"

    private var mRealm: Realm? = null

    private fun _init() {
        val defaultConfig = RealmConfiguration.Builder()
                .name("history_data")
                .schemaVersion(1L)
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

    fun latest(): String {
        if (mRealm == null) {
            _init()
        }

        val results = mRealm?.where(HistoryDataTable::class.java)?.findAll()?.sort("dateStr", Sort.DESCENDING)

        var date: String = ""
        if (null != results && results.size > 0) {
            date = results.first()?.dateStr!!
        }
        _close()

        return date
    }

    fun add(dateStr: String) {
        if (mRealm == null) {
            _init()
        }

        val t = HistoryDataTable()
        t.dateStr = dateStr

        mRealm?.executeTransaction {
            it.copyToRealmOrUpdate(t)
        }
        _close()
    }


    fun addAll(date: ArrayList<String>) {
        if (mRealm == null) {
            _init()
        }

        mRealm?.executeTransaction {
            realm ->
            date.forEach {
                val t = HistoryDataTable()
                t.dateStr = it
                realm.copyToRealmOrUpdate(t)
            }
        }

        _close()
    }

    companion object {
        private var _d: HistoryDataDB? = null
        fun newInstance(): HistoryDataDB? {
            if (null == _d) {
                _d = HistoryDataDB()
            }
            return _d
        }
    }
}
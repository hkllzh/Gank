package com.hkllzh.gank.db

import io.realm.Sort
import java.util.*

/**
 * Created by lizheng on 2017/2/10.
 */
class HistoryDataDB private constructor() : BaseDB() {

    override fun getDBName(): String {
        return dbName
    }

    override fun getDBVersion(): Long {
        return dbVersion
    }

    private val debug = true
    private val TAG = "HistoryDataDB"

    private val dbName = "history_data.realm"
    private val dbVersion = 1L

    fun latest(): String {

        var date: String = ""
        _action {
            val results = mRealm?.where(HistoryDataTable::class.java)?.findAll()?.sort("dateStr", Sort.DESCENDING)
            if (null != results && results.size > 0) {
                date = results.first()?.dateStr!!
            }
        }

        return date
    }

    fun add(dateStr: String) {
        _action {
            val t = HistoryDataTable()
            t.dateStr = dateStr

            mRealm?.executeTransaction {
                it.copyToRealmOrUpdate(t)
            }
        }
    }

    fun addAll(date: ArrayList<String>) {
        _action {
            mRealm?.executeTransaction {
                realm ->
                date.forEach {
                    val t = HistoryDataTable()
                    t.dateStr = it
                    realm.copyToRealmOrUpdate(t)
                }
            }
        }
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
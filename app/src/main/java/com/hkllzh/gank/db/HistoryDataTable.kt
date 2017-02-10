package com.hkllzh.gank.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by lizheng on 2017/2/10.
 */

open class HistoryDataTable : RealmObject() {
    @PrimaryKey
    var dateStr: String = ""
    override fun toString(): String {
        return "HistoryDataTable(dateStr='$dateStr')"
    }
}
package com.hkllzh.gank.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by lizheng on 2017/2/14.
 */
open class ContentTable : RealmObject() {
    @PrimaryKey
    var dateStr: String = ""

    var content: String = ""
}
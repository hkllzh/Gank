package com.hkllzh.gank.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by lizheng on 2017/2/15.
 */
open class FavTable : RealmObject() {
    @PrimaryKey
    var _id: String = ""
    // Json对象的字符串
    var content: String = ""
}
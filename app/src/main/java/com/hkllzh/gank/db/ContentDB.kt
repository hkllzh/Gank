package com.hkllzh.gank.db

/**
 * Created by lizheng on 2017/2/14.
 */
class ContentDB private constructor() : BaseDB() {
    override fun getDBName(): String {
        return dbName
    }

    override fun getDBVersion(): Long {
        return dbVersion
    }

    private val debug = true
    private val TAG = "ContentDB"

    private val dbName = "content_data"
    private val dbVersion = 1L

    fun add(dateStr: String, content: String) {
        _action {
            val d = ContentTable()
            d.content = content
            d.dateStr = dateStr

            mRealm?.executeTransaction {
                it.copyToRealmOrUpdate(d)
            }
        }
    }

    fun findContentByDate(dateStr: String): String {
        var content: String = ""

        _action {
            val results = mRealm?.where(ContentTable::class.java)?.equalTo("dateStr", dateStr)?.findFirst()
            content = results?.content!!
        }

        return content
    }


    companion object {
        private var _temp: ContentDB? = null
        fun newInstance(): ContentDB? {
            if (null == _temp) {
                _temp = ContentDB()
            }
            return _temp
        }
    }
}
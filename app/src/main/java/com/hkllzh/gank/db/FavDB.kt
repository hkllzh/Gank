package com.hkllzh.gank.db

import com.google.gson.Gson
import com.hkllzh.gank.adapter.item.category_content.CategoryContent
import com.hkllzh.gank.bean.SingleContent
import java.util.*

/**
 * Created by lizheng on 2017/2/15.
 */
class FavDB : BaseDB() {
    override fun getDBName(): String {
        return "fav_db.realm"
    }

    override fun getDBVersion(): Long {
        return 1L
    }

    fun add(content: CategoryContent) {
        _action {
            val table = FavTable()
            table._id = content.content._id
            table.content = Gson().toJson(content.content)
            mRealm?.executeTransaction {
                mRealm?.copyToRealmOrUpdate(table)
            }
        }
    }

    fun findAll(): ArrayList<CategoryContent> {
        val list = ArrayList<CategoryContent>()

        _action {
            mRealm?.where(FavTable::class.java)?.findAll()?.forEach {
                list.add(CategoryContent(Gson().fromJson(it.content, SingleContent::class.java)))
            }
        }

        return list
    }

    companion object {
        private var _d: FavDB? = null
        fun newInstance(): FavDB? {
            if (null == _d) {
                _d = FavDB()
            }
            return _d
        }
    }

}
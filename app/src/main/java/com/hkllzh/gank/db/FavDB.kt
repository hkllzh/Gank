package com.hkllzh.gank.db

import android.util.Log
import com.google.gson.Gson
import com.hkllzh.gank.adapter.item.category_content.CategoryContent
import com.hkllzh.gank.bean.SingleContent
import com.hkllzh.gank.event.FavChangeEvent
import com.hkllzh.gank.util.RxBus
import java.util.*

/**
 * Created by lizheng on 2017/2/15.
 */
class FavDB : BaseDB() {

    private val TAG = "FavDB"

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
        RxBus.getDefault().post(FavChangeEvent())
    }

    fun findAll(): ArrayList<CategoryContent> {
        val list = ArrayList<CategoryContent>()

        _action {
            mRealm?.where(FavTable::class.java)?.findAll()?.forEach {
                list.add(CategoryContent(Gson().fromJson(it.content, SingleContent::class.java)))
            }
        }

        Collections.reverse(list)

        return list
    }

    fun deleteFav(content: CategoryContent) {
        _action {
            mRealm?.executeTransaction {
                mRealm?.where(FavTable::class.java)?.equalTo("_id", content.content._id)?.findFirst()?.deleteFromRealm()
            }
        }
        RxBus.getDefault().post(FavChangeEvent())
    }

    fun haveFav(content: CategoryContent): Boolean {
        var _temp = false

        _action {
            mRealm?.executeTransaction {
                val r = mRealm?.where(FavTable::class.java)?.equalTo("_id", content.content._id)?.findFirst()
                Log.d(TAG, "-----> " + r?.content)
                _temp = null != r
                Log.d(TAG, "-----> temp1->" + _temp)
            }
        }
        Log.d(TAG, "-----> temp2->" + _temp)
        return _temp
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
package com.hkllzh.gank.net

import android.util.Log
import com.google.gson.JsonObject
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by lizheng on 2017/2/10.
 */

private val TAG = "GankRequest"
private val debug = false

fun haveDataHistory(): Observable<ArrayList<String>> {
    return APIManager.getApi(GankApi::class.java).haveDataHistory()
            .subscribeOn(Schedulers.io())
            .map(::haveDataHistory_jsonData2DateList)
            .observeOn(AndroidSchedulers.mainThread())
}

private fun haveDataHistory_jsonData2DateList(jsonObject: JsonObject): ArrayList<String> {
    if (debug)
        Log.d(TAG, jsonObject.toString())
    if (jsonObject.has("results") && jsonObject.get("results").isJsonArray) {
        val results = jsonObject.get("results").asJsonArray
        val _temp = ArrayList<String>()
        results.forEach {
            _temp.add(it.asString)
        }
        if (debug)
            Log.d(TAG, _temp.toString())
        return _temp
    } else {
        return ArrayList()
    }

}
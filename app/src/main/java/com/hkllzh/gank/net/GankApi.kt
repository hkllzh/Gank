package com.hkllzh.gank.net

import com.google.gson.JsonObject
import retrofit2.http.GET
import rx.Observable

/**
 * Created by lizheng on 2017/2/8.
 */

interface GankApi {
    @GET("Android/10/1")
    fun login(): Observable<JsonObject>
}

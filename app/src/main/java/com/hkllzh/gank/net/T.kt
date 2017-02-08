package com.hkllzh.gank.net

import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable
import java.util.*

/**
 * Created by lizheng on 2017/2/8.
 */

interface GankApi {
    @POST("/login")
    fun login(@Body map: HashMap<String, Any>): Observable<String>
}

package com.hkllzh.gank.net

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by lizheng on 2017/2/8.
 */

interface GankApi {

    /**
     * 分类数据
     * example url http://gank.io/api/data/Android/10/1
     *
     * @param category 类型
     * @param count 数据数目
     * @param page 页码
     */
    @GET("data/{category}/{count}/{page}")
    fun category(@Path("category") category: String,
                 @Path("count") count: Int = 10,
                 @Path("page") page: Int): Observable<JsonObject>

    /**
     * 搜索接口
     * example url : http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     *
     * @param content 搜索内容
     * @param category 搜索类别
     * @param count 搜索条数
     * @param page 搜索结果的第几页
     */
    @GET("search/query/{content}/category/{category}/count/{count}/page/{page}")
    fun search(@Path("content") content: String,
               @Path("category") category: String = "all",
               @Path("count") count: Int = 10,
               @Path("page") page: Int): Observable<JsonObject>

    /**
     * 随机数据
     * example url : http://gank.io/api/random/data/Android/20
     * 返回的格式化数据
     *
     * @param category 类别
     * @param count 数据个数
     */
    @GET("random/data/{category}/{count}")
    fun random(@Path("category") category: String,
               @Path("count") count: Int = 10): Observable<JsonObject>

    /**
     * 每天的历史数据
     * example url : http://gank.io/api/day/2015/08/06
     * 返回的格式化数据
     *
     * @param year 年
     * @param month 月
     * @param day 日
     */
    @GET("day/{year}/{month}/{day}")
    fun day(year: Int, month: Int, day: Int): Observable<JsonObject>


    /**
     * 历史数据
     * 返回的是网页格式数据
     * example url : http://gank.io/api/history/content/10/2
     *
     * @param count 每次的数据个数
     * @param page 第几页
     */
    @GET("history/content/{count}/{page}")
    fun history(@Path("count") count: Int = 10,
                @Path("page") page: Int): Observable<JsonObject>

    /**
     * 每天的历史数据
     * 返回的是网页格式数据
     * example url : http://gank.io/api/history/content/day/2016/05/11
     *
     * @param year 年
     * @param month 月
     * @param day 日
     */
    @GET("history/content/day/{year}/{month}/{day}")
    fun historyDay(year: Int, month: Int, day: Int): Observable<JsonObject>

    /**
     * 干货日期接口
     * example : http://gank.io/api/day/history
     */
    @GET("day/history")
    fun haveDataHistory(): Observable<JsonObject>
}

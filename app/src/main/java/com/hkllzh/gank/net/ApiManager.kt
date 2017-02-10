package com.hkllzh.gank.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Created by lizheng on 2017/2/9.
 */
class APIManager {

    companion object {
        private val TIMEOUT_READ = 30L
        private val TIMEOUT_CONNECTION = 30L


        private var client: OkHttpClient? = null;
        private val APICache = ConcurrentHashMap<String, Any>()
        private var sIsDebugModel = false

        fun <T> getApi(clazz: Class<T>): T {
            if (null == client) {
                client = getOkHttpClient()
            }
            var api: T = APICache[clazz.name] as T

            if (null == api) {
                synchronized(APIManager::class.java) {
                    api = Retrofit.Builder()
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl("http://gank.io/api/")
                            .build().create(clazz)
                    APICache.put(clazz.name, api!!)
                }
            }

            return api
        }

        private fun getOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                    //设置Cache目录
                    // .cache(CacheUtil.getCache(UIUtil.getContext()))
                    //设置缓存
                    // .addInterceptor(cacheInterceptor)
                    // .addNetworkInterceptor(cacheInterceptor)
                    //失败重连
                    .retryOnConnectionFailure(true)
                    //time out
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    //打印日志
                    .addInterceptor(loggingInterceptor)
                    .build()
        }
    }

//    public static <T> T getAPI(Class<T> clazz) {
//        if (null == client) {
//            client = getOkHttpClient();
//        }
//        T api = (T) APICache.get(clazz.getTabTitle());
//        if (api == null) {
//            synchronized (APIManager.class) {
//                if (api != null) return api;
//                api = new Retrofit.Builder().client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl(Constant.Net.BASE_URL)
//                .build().create(clazz);
//                APICache.put(clazz.getTabTitle(), api);
//            }
//        }
//        return api;
//    }

}
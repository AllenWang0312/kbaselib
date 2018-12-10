package edu.tjrac.swant.kotlin.baselib.common

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by wpc on 2018-12-10.
 */

open class BaseNet{
    companion object {
        //kotlin 线程安全单例
         var retrofitMap = HashMap<String, Retrofit>()
         var sOkHttpClient: OkHttpClient? = null
    }

    private val mRetrofitLock = Object()

    fun getRetrofit(url: String): Retrofit? {
        synchronized(mRetrofitLock) {
            if (null === retrofitMap.get(url)) {
                var retrofit = Retrofit.Builder().client(getOkHttpClient())
                        .baseUrl(url)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofitMap.put(url, retrofit)
                return retrofit
            } else {
                return retrofitMap.get(url)
            }
        }
    }
    open fun getOkHttpClient(): OkHttpClient {
        if (null === sOkHttpClient) {
            synchronized(BaseNet::class.java) {
                var cache = Cache(File(BaseApplication.Companion.instance!!.cacheDir, "HttpCache")
                        , 1024 * 1024 * 100)
                if (null === sOkHttpClient) {
                    sOkHttpClient = OkHttpClient.Builder()
                            .cache(cache)
                            .readTimeout(60, TimeUnit.SECONDS)
//                            .addInterceptor(getLoggingInterceptor())
                            .build()
                }
            }
        }
        return sOkHttpClient!!;
    }
}
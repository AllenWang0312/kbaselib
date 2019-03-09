package edu.tjrac.swant.baselib.common.base

import okhttp3.*
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by wpc on 2018-12-10.
 */

abstract class BaseNet {
    companion object {
        //kotlin 线程安全单例
        var retrofitMap = HashMap<String, Retrofit>()
        var sOkHttpClient: OkHttpClient? = null
    }

    protected val mRetrofitLock = Object()

    open fun getRetrofit(url: String): Retrofit? {
        synchronized(mRetrofitLock) {
            if (null === retrofitMap.get(url)) {
                var retrofit = Retrofit.Builder().client(getOkHttpClient())
                        .baseUrl(url)
//                        .addConverterFactory(GsonResponseConverterFactory.create())
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
                var cache = Cache(File(BaseApplication.instance!!.cacheDir, "HttpCache")
                        , 1024 * 1024 * 100)
                if (null === sOkHttpClient) {
                    sOkHttpClient = OkHttpClient.Builder()
                            .cache(cache)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(getInterceptor())

                            .build()
                }
            }
        }
        return sOkHttpClient!!;
    }

    /**
     * 设置公共参数
     */
    abstract fun getInterceptor(): Interceptor

}
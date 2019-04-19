package edu.tjrac.swant.baselib.common.base.net

import edu.tjrac.swant.baselib.common.base.BaseApplication
import edu.tjrac.swant.baselib.util.StringUtils
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(getInterceptor())
                            .build()
                }
            }
        }
        return sOkHttpClient!!
    }

    /**
     * 设置公共参数
     */
 open   fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
//                    .addQueryParameter("platform", "android")
//                    .addQueryParameter("version", BuildConfig.VERSION_NAME)
                    .build()
            var builder = originalRequest.newBuilder()
//            if (!StringUtils.isEmpty(App_v4.token)) {
//                builder.addHeader("token", App_v4.token)
//            }
            request = builder.url(modifiedUrl).build()
            chain.proceed(request)
        }
    }
}
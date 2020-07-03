package edu.tjrac.swant.baselib.common.base.net

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import edu.tjrac.swant.baselib.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

/**
 * Created by wpc on 2018-12-27.
 */

class CustomizeGsonResponseBodyConverter<T> internal constructor(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        //把responsebody转为string
        val response = value.string()
        if(null!=response&& response.isNotEmpty()){
            if (BuildConfig.DEBUG) {
                //打印后台数据
                Log.e(BuildConfig.LIBRARY_PACKAGE_NAME, response)
            }
            try {
                return adapter.fromJson(response)
            } finally {
                value.close()
            }
        }
      return null
    }
}

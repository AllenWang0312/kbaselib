package edu.tjrac.swant.baselib.common.base

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

import edu.tjrac.swant.baselib.common.base.gson.FloatTypeAdapter
import edu.tjrac.swant.baselib.common.base.gson.IntegerTypeAdapter
import edu.tjrac.swant.baselib.common.base.gson.StringTypeAdapter
import edu.tjrac.swant.baselib.common.base.net.CustomizeGsonResponseBodyConverter
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Created by wpc on 2018-12-27.
 */

class CustomizeGsonConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {

    init {
        if (gson == null) {
            throw NullPointerException("gson == null")
        }
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val adapter = gson.getAdapter(TypeToken.get(type!!))
        return CustomizeGsonResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type!!))
        return CustomizeGsonRequestBodyConverter(gson, adapter)
    }

    companion object {

        fun createGson(): Gson {
            return GsonBuilder()
                    .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerTypeAdapter())
                    .registerTypeAdapter(Int::class.java, IntegerTypeAdapter())
                    .registerTypeAdapter(String::class.java, StringTypeAdapter())
                    .registerTypeAdapter(Float::class.javaPrimitiveType, FloatTypeAdapter())
                    .registerTypeAdapter(Float::class.java, FloatTypeAdapter())
                    .create()
        }

        @JvmOverloads
        fun create(gson: Gson = createGson()): CustomizeGsonConverterFactory {
            return CustomizeGsonConverterFactory(gson)
        }
    }

}

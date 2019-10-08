package edu.tjrac.swant.baselib.common.base.gson

import android.util.Log

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import java.io.IOException

/**
 * Created by wpc on 2018-12-27.
 */

class StringTypeAdapter : TypeAdapter<String>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: String?) {
        var value = value
        try {
            if (value == null) {
                value = ""
            }
            out.value(value as String?)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun read(`in`: JsonReader): String {
        try {
            val value: Int?
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                Log.e("TypeAdapter", "null is not a number")
                return ""
            }
            if (`in`.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                Log.e("TypeAdapter", "$b is not a number")
                return b.toString()
            }
            if (`in`.peek() == JsonToken.STRING) {
                return `in`.nextString()
            } else {
                value = `in`.nextInt()
                return value.toString()
            }
        } catch (e: Exception) {
            Log.e("TypeAdapter", "Not a number", e)
        }

        return ""
    }
}

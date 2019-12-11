package edu.tjrac.swant.baselib.common.base.gson

import android.util.Log

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import edu.tjrac.swant.baselib.util.SUtil

/**
 * Created by wpc on 2018-12-27.
 */

class FloatTypeAdapter : TypeAdapter<Float>() {
    override fun write(out: JsonWriter, value: Float?) {
        var value = value
        try {
            if (value == null) {
                value = 0f
            }
            out.value((value as Float).toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun read(`in`: JsonReader): Float? {
        try {
            val value: Float?
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                //                Log.e("TypeAdapter", "null is not a number");
                return 0f
            }
            if (`in`.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                //                Log.e("TypeAdapter", b + " is not a number");
                return 0f
            }
            if (`in`.peek() == JsonToken.STRING) {
                val str = `in`.nextString()
                if (SUtil.isDouble(str)) {
                    return java.lang.Float.parseFloat(str)
                } else {
                    Log.e("TypeAdapter", "$str is not a number")
                    return 0f
                }
            } else {
                val str = `in`.nextString()
                value = java.lang.Float.valueOf(str)
            }
            return value
        } catch (e: Exception) {
            Log.e("TypeAdapter", "Not a number", e)
        }

        return 0f
    }
}
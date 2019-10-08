package edu.tjrac.swant.baselib.common.base.gson

import android.util.Log

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import edu.tjrac.swant.baselib.util.StringUtils

/**
 * Created by wpc on 2018-12-27.
 */

class DoubleTypeAdapter : TypeAdapter<Double>() {
    override fun write(out: JsonWriter, value: Double?) {
        var value = value
        try {
            if (value == null) {
                value = 0.0
            }
            out.value(value as Double)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun read(`in`: JsonReader): Double? {
        try {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                Log.e("TypeAdapter", "null is not a number")
                return 0.0
            }
            if (`in`.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                Log.e("TypeAdapter", "$b is not a number")
                return 0.0
            }
            if (`in`.peek() == JsonToken.STRING) {
                val str = `in`.nextString()
                if (StringUtils.isDouble(str)) {
                    return java.lang.Double.parseDouble(str)
                } else {
                    Log.e("TypeAdapter", "$str is not a number")
                    return 0.0
                }
            } else {
                val value = `in`.nextDouble()
                return value ?: 0.0
            }
        } catch (e: NumberFormatException) {
            Log.e("TypeAdapter", e.message, e)
        } catch (e: Exception) {
            Log.e("TypeAdapter", e.message, e)
        }

        return 0.0
    }
}
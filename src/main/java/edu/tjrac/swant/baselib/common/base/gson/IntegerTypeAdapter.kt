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

class IntegerTypeAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter, value: Int?) {
        var value = value
        try {
            if (value == null) {
                value = 0
            }
            out.value((value as Int).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun read(`in`: JsonReader): Int? {
        try {
            val value: Int?
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                Log.e("TypeAdapter", "null is not a number")
                return 0
            } else if (`in`.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                Log.e("TypeAdapter", "$b is not a number")
                return 0
            } else if (`in`.peek() == JsonToken.STRING) {
                val str = `in`.nextString()
                if (null == str || SUtil.isEmpty(str)) {
                    return 0
                } else {
                    if (SUtil.isInteger(str)) {
                        return Integer.parseInt(str)
                    } else {
                        Log.e("TypeAdapter", "$str is not a int number")
                        return 0
                    }
                }
            } else {
                value = `in`.nextInt()
                return value
            }
        } catch (e: Exception) {
            Log.e("TypeAdapter", "Not a number", e)
        }

        return 0
    }

}
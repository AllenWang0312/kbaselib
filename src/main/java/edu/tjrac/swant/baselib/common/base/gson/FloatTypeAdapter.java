package edu.tjrac.swant.baselib.common.base.gson;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import edu.tjrac.swant.baselib.util.StringUtils;

/**
 * Created by wpc on 2018-12-27.
 */

public class FloatTypeAdapter extends TypeAdapter {
    @Override
    public void write(JsonWriter out, Object value) {
        try {
            if (value == null){
                value = 0F;
            }
            out.value((float)value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Float read(JsonReader in) {
        try {
            Float value;
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
//                Log.e("TypeAdapter", "null is not a number");
                return 0F;
            }
            if (in.peek() == JsonToken.BOOLEAN) {
                boolean b = in.nextBoolean();
//                Log.e("TypeAdapter", b + " is not a number");
                return 0F;
            }
            if (in.peek() == JsonToken.STRING) {
                String str = in.nextString();
                if (StringUtils.Companion.isDouble(str)){
                    return Float.parseFloat(str);
                } else {
                    Log.e("TypeAdapter", str + " is not a number");
                    return 0F;
                }
            } else {
                String str = in.nextString();
                value = Float.valueOf(str);
            }
            return value;
        } catch (Exception e) {
            Log.e("TypeAdapter", "Not a number", e);
        }
        return 0F;
    }
}
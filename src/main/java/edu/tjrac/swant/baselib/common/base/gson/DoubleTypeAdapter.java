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

public class DoubleTypeAdapter extends TypeAdapter {
    @Override
    public void write(JsonWriter out, Object value) {
        try {
            if (value == null){
                value = 0D;
            }
            out.value((double)value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double read(JsonReader in) {
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                Log.e("TypeAdapter", "null is not a number");
                return 0D;
            }
            if (in.peek() == JsonToken.BOOLEAN) {
                boolean b = in.nextBoolean();
                Log.e("TypeAdapter", b + " is not a number");
                return 0D;
            }
            if (in.peek() == JsonToken.STRING) {
                String str = in.nextString();
                if (StringUtils.Companion.isDouble(str)){
                    return Double.parseDouble(str);
                } else {
                    Log.e("TypeAdapter", str + " is not a number");
                    return 0D;
                }
            } else {
                Double value = in.nextDouble();
                return value == null ? 0D : value;
            }
        }catch(NumberFormatException e){
            Log.e("TypeAdapter", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("TypeAdapter", e.getMessage(), e);
        }
        return 0D;
    }
}
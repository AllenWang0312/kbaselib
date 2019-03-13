package edu.tjrac.swant.baselib.common.base.gson;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import edu.tjrac.swant.baselib.util.StringUtils;

/**
 * Created by wpc on 2018-12-27.
 */

public class IntegerTypeAdapter extends TypeAdapter {
    @Override
    public void write(JsonWriter out, Object value) throws IOException {
        try {
            if (value == null){
                value = 0;
            }
            out.value((int)value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer read(JsonReader in) {
        try {
            Integer value;
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                Log.e("TypeAdapter", "null is not a number");
                return 0;
            }else if (in.peek() == JsonToken.BOOLEAN) {
                boolean b = in.nextBoolean();
                Log.e("TypeAdapter", b + " is not a number");
                return 0;
            }else if (in.peek() == JsonToken.STRING) {
                String str = in.nextString();
                if(StringUtils.Companion.isEmpty(str)){
                    return 0;
                }else {
                    if (StringUtils.Companion.isInteger(str)){
                        return Integer.parseInt(str);
                    } else {
                        Log.e("TypeAdapter", str + " is not a int number");
                        return 0;
                    }
                }
            } else {
                value = in.nextInt();
                return value;
            }
        } catch (Exception e) {
            Log.e("TypeAdapter", "Not a number", e);
        }
        return 0;
    }

}
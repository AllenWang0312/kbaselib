package edu.tjrac.swant.baselib.common.base.gson;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by wpc on 2018-12-27.
 */

public class StringTypeAdapter extends TypeAdapter {
    @Override
    public void write(JsonWriter out, Object value) throws IOException {
        try {
            if (value == null) {
                value = "";
            }
            out.value((String) value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String read(JsonReader in) {
        try {
            Integer value;
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                Log.e("TypeAdapter", "null is not a number");
                return "";
            }
            if (in.peek() == JsonToken.BOOLEAN) {
                boolean b = in.nextBoolean();
                Log.e("TypeAdapter", b + " is not a number");
                return String.valueOf(b);
            }
            if (in.peek() == JsonToken.STRING) {
                return in.nextString();
            } else {
                value = in.nextInt();
                return value.toString();
            }
        } catch (Exception e) {
            Log.e("TypeAdapter", "Not a number", e);
        }
        return "";
    }
}

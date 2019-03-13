package edu.tjrac.swant.baselib.common.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;


import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import edu.tjrac.swant.baselib.common.base.gson.FloatTypeAdapter;
import edu.tjrac.swant.baselib.common.base.gson.IntegerTypeAdapter;
import edu.tjrac.swant.baselib.common.base.gson.StringTypeAdapter;
import edu.tjrac.swant.baselib.common.base.net.CustomizeGsonResponseBodyConverter;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by wpc on 2018-12-27.
 */

public class CustomizeGsonConverterFactory extends Converter.Factory {
    public static CustomizeGsonConverterFactory create() {
        return create(createGson());
    }

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntegerTypeAdapter())
                .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())

                .registerTypeAdapter(String.class, new StringTypeAdapter())

                .registerTypeAdapter(float.class, new FloatTypeAdapter())
                .registerTypeAdapter(Float.class, new FloatTypeAdapter())
                .create();
    }

    public static CustomizeGsonConverterFactory create(Gson gson) {
        return new CustomizeGsonConverterFactory(gson);
    }

    private final Gson gson;

    private CustomizeGsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomizeGsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomizeGsonRequestBodyConverter<>(gson, adapter);
    }

}

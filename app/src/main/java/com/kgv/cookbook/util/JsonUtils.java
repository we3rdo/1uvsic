package com.kgv.cookbook.util;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * @author KEXUAN CHEN
 * @time 2016/10/26  16:16
 * @desc ${TODD}
 */
public class JsonUtils {

    private static Gson gson;

    public static <T> T parse(String json, Class<T> tClass) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        if (gson == null) {
            gson = new Gson();
        }

        return gson.fromJson(json, tClass);
    }
}

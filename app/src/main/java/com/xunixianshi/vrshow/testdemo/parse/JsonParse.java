package com.xunixianshi.vrshow.testdemo.parse;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JsonParse implements ResponseParse {

    @Override
    public <E> E paserObject(Object result, Class<E> clazz) {
        if (!isJsonObject(result.toString())) {
            return null;
        }
        return new Gson().fromJson(result.toString(), clazz);
    }

    @Override
    public <E> E paserCollection(Object result, Type typeToken) {
        if (!isJsonArray(result.toString())) {
            return null;
        }

        return new Gson().fromJson(result.toString(), typeToken);
        /**
         * new TypeToken<T>() {
         *         }.getType()
         */
    }

    @Override
    public String paserJson(Object object) {
        return new Gson().toJson(object);
    }


    public static boolean isJsonObject(String content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        boolean isJsonObject = true;
        try {
            JSONObject jsonObject = new JSONObject(content);
        } catch (JSONException e) {
            isJsonObject = false;
            e.printStackTrace();
        }
        return isJsonObject;

    }


    public static boolean isJsonArray(String content) {

        if (TextUtils.isEmpty(content)) {
            return false;
        }

        boolean isJSONArray = true;
        try {
            JSONArray jsonObject = new JSONArray(content);
        } catch (JSONException e) {
            isJSONArray = false;
            e.printStackTrace();
        }
        return isJSONArray;

    }

}

package com.xunixianshi.filelibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @Description: TODO  SharedPreference工具类
 * @Author: hch
 * @CreateDate: 2020/9/17 16:03
 * @Version: 1.0
 */
public class SimpleSharedPreferences {

    public static long getInt(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return prefs.getLong(key, 0);
    }

    public static long getInt(String key, Context ctx, int defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return prefs.getLong(key, defValue);
    }

    public static void remove(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        prefs.edit().remove(key).commit();
    }

    public static void putInt(String key, long intVal, Context ctx) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor mEditor = prefs.edit();
        mEditor.putLong(key, intVal);
        mEditor.commit();
    }

    public static void putString(String key, String val, Context ctx) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor mEditor = prefs.edit();
        mEditor.putString(key, val);

        mEditor.commit();
    }

    public static String getString(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return prefs.getString(key, "");
    }

    public static String getString(String key, Context ctx, String defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return prefs.getString(key, defValue);
    }

    public static void putBoolean(String key, boolean bool, Context ctx) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor mEditor = prefs.edit();
        mEditor.putBoolean(key, bool);
        mEditor.commit();
    }

    public static boolean getBoolean(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return prefs.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, Context ctx, boolean defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return prefs.getBoolean(key, defValue);
    }
}

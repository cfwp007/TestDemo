package com.xunixianshi.filelibs.work;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * @author zyb
 */
public final class SharedPreferencesSavaData {
    /**
     * SharedPreferences文件名
     */
    private static final String PREFERENCE_NAME = "client_preferences";
    private static SharedPreferencesSavaData instance;
    private SharedPreferences mSharedPreferences;

    private SharedPreferencesSavaData(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static SharedPreferencesSavaData initInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesSavaData(context);
        }
        return instance;
    }

    public synchronized static SharedPreferencesSavaData getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesSavaData(context);
        }
        return instance;
    }


    public void remove(String key) {
        mSharedPreferences.edit().remove(key).commit();
    }

    /**
     * 保存字符串
     */
    public void saveString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 获取字符串
     */
    public String getString(String key, String... defValue) {
        if (defValue.length > 0) {
            return mSharedPreferences.getString(key, defValue[0]);
        } else {
            return mSharedPreferences.getString(key, "");
        }
    }

    /**
     * 获取布尔值
     */
    public Boolean getBoolean(String key, Boolean... defValue) {
        if (defValue.length > 0) {
            return mSharedPreferences.getBoolean(key, defValue[0]);
        } else {
            return mSharedPreferences.getBoolean(key, false);
        }
    }

    /**
     * 保存布尔值
     */
    public void saveBoolean(String key, Boolean defValue) {
        mSharedPreferences.edit().putBoolean(key, defValue).apply();
    }


    /**
     * 获取Int
     */
    public int getInt(String key, int... defValue) {
        if (defValue.length > 0) {
            return mSharedPreferences.getInt(key, defValue[0]);
        } else {
            return mSharedPreferences.getInt(key, 0);
        }
    }

    /**
     * 保存Int
     */
    public void saveInt(String key, int defValue) {
        mSharedPreferences.edit().putInt(key, defValue).apply();
    }

    /**
     * 获取Long
     */
    public long getLong(String key, long... defValue) {
        if (defValue.length > 0) {
            return mSharedPreferences.getLong(key, defValue[0]);
        } else {
            return mSharedPreferences.getLong(key, 0);
        }
    }

    /**
     * 保存Long
     */
    public void saveLong(String key, long defValue) {
        mSharedPreferences.edit().putLong(key, defValue).apply();
    }

}

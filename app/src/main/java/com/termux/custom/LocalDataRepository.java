package com.termux.custom;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LiZhe on 2018-01-03.
 */

public class LocalDataRepository {

    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_GIT_NAME = "user_git_name";
    public static final String USER_GIT_PASSWORD = "user_git_password";
    public static final String GIT_ADDRESS = "git_address";

    private static volatile LocalDataRepository mInstance;
    private final SharedPreferences preferences;

    public static LocalDataRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LocalDataRepository.class) {
                if (mInstance == null) {
                    mInstance = new LocalDataRepository(context);
                }
            }
        }
        return mInstance;
    }

    private LocalDataRepository(Context context) {
        preferences = context.getSharedPreferences("com.lizheblogs.git.data", Context.MODE_PRIVATE);
    }

    /**
     * 本地存储数据
     *
     * @param keyString 标识
     * @param value     数据
     */
    public void setInt(String keyString, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(keyString, value);
        editor.apply();
    }

    /**
     * 获取存储的数据
     *
     * @param keyString 标识
     * @return 数据，默认为0
     */
    public int getInt(String keyString) {
        return preferences.getInt(keyString, 0);
    }

    public int getInt(String keyString, int defValue) {
        return preferences.getInt(keyString, defValue);
    }

    /**
     * 本地存储数据
     *
     * @param keyString 标识
     * @param value     数据
     */
    public void setString(String keyString, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(keyString, value);
        editor.apply();
    }

    /**
     * 获取存储的数据
     *
     * @param keyString 标识
     * @return 数据，默认为0
     */
    public String getString(String keyString) {
        return preferences.getString(keyString, "");
    }

}

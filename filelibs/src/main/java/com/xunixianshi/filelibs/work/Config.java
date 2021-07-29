package com.xunixianshi.filelibs.work;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class Config {
    public static final String AUTH_JSON= "AUTH_JSON";//认证数据
    public static final String VOICE100= "Voice100";//设置媒体音量
    public static final String LOGIN_STATUS= "LOGIN_STATUS";//是否退出登录 true退出登录
    public static final String PHONE_STATUS= "PHONE_STATUS";//获取手机状态信息
    public static final String NETWORK_STATUS= "NetWork_status";//网络状态栏是否显示过
    public static final String NETWORK_STATUS_YES= "NETWORK_STATUS_YES";//网络状态栏显示过
    public static final String NETWORK_STATUS_NO= "NETWORK_STATUS_NO";//网络状态栏未显示过
    public static final String VIDEO_DATA= "VIDEO_DATA";//首页数据
    public static final String VIDEO_TITLE= "VIDEO_TITLE";//首页分类数据
    public static final String RESULT_JSON= "resultJson";//本地视频数据
    public static final int YES = 1;
    public static final int NO =0;


    public static final String ACTION_UNITY_DATA = "com.xunixianshi.filelibs.ACTION_GET_UNITY_DATA.Action";//向Unity提供当前手机相关数据。信号、蓝牙是否打开、当前手机音量

    public static String getVideoPath(Context context){
        String PATH = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "Scanner";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH = context.getFilesDir().getAbsolutePath()
                    + File.separator + "Scanner";
        }
        return PATH;
    }

}

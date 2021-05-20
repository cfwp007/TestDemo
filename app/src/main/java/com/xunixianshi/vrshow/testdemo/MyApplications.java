package com.xunixianshi.vrshow.testdemo;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import dagger.hilt.android.HiltAndroidApp;

/**
 * @ClassName: MyApplications$
 * @Description: java类作用描述
 * @Author: wpeng
 * @CreateDate: 2021/5/17$ 15:09$
 * @Version: 1.0
 */
@HiltAndroidApp//初始化hilt
public class MyApplications extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}

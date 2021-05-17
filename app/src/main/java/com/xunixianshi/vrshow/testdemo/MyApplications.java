package com.xunixianshi.vrshow.testdemo;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * @ClassName: MyApplications$
 * @Description: java类作用描述
 * @Author: wpeng
 * @CreateDate: 2021/5/17$ 15:09$
 * @Version: 1.0
 */
public class MyApplications extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}

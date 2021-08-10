package com.xunixianshi.vrshow.testdemo.httpdemo

import com.xunixianshi.vrshow.testdemo.obj.PersonLive

/**
 * @ClassName:      ApiHelper$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/10$ 10:51$
 * @Version:        1.0
 */
interface ApiHelper {
    suspend fun getUsers(): List<PersonLive>
}
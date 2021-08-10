package com.xunixianshi.vrshow.testdemo.httpdemo

import com.xunixianshi.vrshow.testdemo.http.ApiService
import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import javax.inject.Inject

/**
 * @ClassName:      ApiHelperImpl$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/10$ 10:49$
 * @Version:        1.0
 */

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUsers(): List<PersonLive>  = apiService.getUsers()
}
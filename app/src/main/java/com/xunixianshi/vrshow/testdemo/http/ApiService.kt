package com.xunixianshi.vrshow.testdemo.http

import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import retrofit2.Response
import retrofit2.http.GET

/**
 * @ClassName:      ApiService$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/19$ 9:21$
 * @Version:        1.0
 */
interface ApiService {
    @GET("user")
    suspend fun getUser(): Response<PersonLive>


    @GET("demo")
    suspend fun getDemo(): Response<List<PersonLive>>
}
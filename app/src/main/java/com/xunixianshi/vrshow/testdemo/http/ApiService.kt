package com.xunixianshi.vrshow.testdemo.http

import com.xunixianshi.vrshow.testdemo.obj.PersonLive
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * @ClassName:      ApiService$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/19$ 9:21$
 * @Version:        1.0
 */
interface ApiService {
    @GET("user")
    suspend fun getUser(@QueryMap map: MutableMap<String,String>): Response<PersonLive>


    @GET("demo")
    suspend fun getDemo(): Response<List<PersonLive>>


    @GET("users")
    suspend fun getUsers(): List<PersonLive>


}
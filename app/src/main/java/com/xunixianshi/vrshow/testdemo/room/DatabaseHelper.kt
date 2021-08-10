package com.xunixianshi.vrshow.testdemo.room


/**
 * @ClassName:      DatabaseHelper$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/10$ 14:48$
 * @Version:        1.0
 */
interface DatabaseHelper {

    suspend fun getUsers(): List<DBUser>

    suspend fun insertAll(users: List<DBUser>)
}
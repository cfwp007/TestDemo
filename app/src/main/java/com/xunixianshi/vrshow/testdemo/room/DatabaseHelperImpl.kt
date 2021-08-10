package com.xunixianshi.vrshow.testdemo.room


/**
 * @ClassName:      DatabaseHelperImpl$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/10$ 14:48$
 * @Version:        1.0
 */
class DatabaseHelperImpl(private val appDatabase: AppDatabase):DatabaseHelper {

    override suspend fun getUsers(): List<DBUser> = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<DBUser>) = appDatabase.userDao().insertAll(users)
}
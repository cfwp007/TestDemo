package com.xunixianshi.vrshow.testdemo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * @ClassName:      UserDao$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/10$ 14:51$
 * @Version:        1.0
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM dbuser")
    suspend fun getAll():List<DBUser>

    @Insert
    suspend fun insertAll(users: List<DBUser>)

    @Delete
    suspend fun delete(user: DBUser)

}
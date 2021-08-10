package com.xunixianshi.vrshow.testdemo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xunixianshi.vrshow.testdemo.obj.PersonLive

/**
 * @ClassName:      AppDatabase$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/8/10$ 14:49$
 * @Version:        1.0
 */
@Database(entities = [DBUser::class],version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun userDao():UserDao
}
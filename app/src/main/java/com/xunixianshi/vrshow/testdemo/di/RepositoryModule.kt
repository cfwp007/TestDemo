package com.xunixianshi.vrshow.testdemo.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.xunixianshi.vrshow.testdemo.UserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * @ClassName:      RepositoryModule$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/19$ 10:07$
 * @Version:        1.0
 */
@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserInfoRepository(sharedPreferences: SharedPreferences) : UserInfoRepository {

        return UserInfoRepository.getInstance(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("DEFAULT_SP", Context.MODE_PRIVATE)
    }
}
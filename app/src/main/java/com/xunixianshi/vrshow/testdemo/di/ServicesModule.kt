package com.xunixianshi.vrshow.testdemo.di

import android.app.Application
import androidx.room.Room
import com.xunixianshi.vrshow.testdemo.MyApplications
import com.xunixianshi.vrshow.testdemo.http.ApiService
import com.xunixianshi.vrshow.testdemo.room.AppDatabase
import com.xunixianshi.vrshow.testdemo.room.DatabaseHelper
import com.xunixianshi.vrshow.testdemo.room.DatabaseHelperImpl
import com.xunixianshi.vrshow.testdemo.service.ServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideServiceManager(userService: ApiService): ServiceManager {
        return ServiceManager(userService)
    }

    @Provides
    @Singleton
    fun provideDBService(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "userdata"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDBHelperService(mAppDatabase :AppDatabase): DatabaseHelper {
        return DatabaseHelperImpl(mAppDatabase)
    }
}

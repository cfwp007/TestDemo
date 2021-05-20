package com.xunixianshi.vrshow.testdemo.di

import com.xunixianshi.vrshow.testdemo.http.ApiService
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
}

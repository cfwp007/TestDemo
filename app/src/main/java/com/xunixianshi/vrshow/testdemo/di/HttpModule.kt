package com.xunixianshi.vrshow.testdemo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xunixianshi.vrshow.testdemo.BuildConfig
import com.xunixianshi.vrshow.testdemo.UserInfoRepository
import com.xunixianshi.vrshow.testdemo.http.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object HttpModule {
    const val TIME_OUT_SECONDS = 10
//    const val BASE_URL = "https://api.github.com/"
    const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(userRepository: UserInfoRepository): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(
                        TIME_OUT_SECONDS.toLong(),
                        TimeUnit.SECONDS)
                .readTimeout(
                        TIME_OUT_SECONDS.toLong(),
                        TimeUnit.SECONDS)
                .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = when (BuildConfig.DEBUG) {
                                true -> HttpLoggingInterceptor.Level.BODY
                                false -> HttpLoggingInterceptor.Level.NONE
                            }
                        }
                )
                .addInterceptor(BasicAuthInterceptor(mUserInfoRepository = userRepository))
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}

package com.lek.mybodyfatpercentage.onboarding.di

import android.content.Context
import android.content.SharedPreferences
import com.lek.mybodyfatpercentage.onboarding.data.UserInfoRepository
import com.lek.mybodyfatpercentage.onboarding.domain.IUserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_INFO_KEY = "body_fat_app_user_info.key"

@Module
@InstallIn(SingletonComponent::class)
object UserInfoRepositoryModule {

    @Provides
    @Singleton
    fun provideUserInfoRepository(sharedPreferences: SharedPreferences): IUserInfoRepository =
        UserInfoRepository(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences(USER_INFO_KEY, Context.MODE_PRIVATE)
}
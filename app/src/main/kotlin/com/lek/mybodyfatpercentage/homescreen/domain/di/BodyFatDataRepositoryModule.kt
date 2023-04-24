package com.lek.mybodyfatpercentage.homescreen.domain.di

import android.content.SharedPreferences
import com.lek.mybodyfatpercentage.homescreen.data.BodyFatDataRepository
import com.lek.mybodyfatpercentage.homescreen.domain.IBodyFatDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BodyFatDataRepositoryModule {

    @Provides
    @Singleton
    fun provideBodyFatDataRepository(
        sharedPreferences: SharedPreferences
    ): IBodyFatDataRepository = BodyFatDataRepository(sharedPreferences)
}
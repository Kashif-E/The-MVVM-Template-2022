package com.example.nocontextviewmodel.di

import com.example.nocontextviewmodel.repository.UserRepository
import com.example.nocontextviewmodel.utils.ResponseHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun getUserRepository() =UserRepository(ResponseHandler())
}
package com.git.example.ntmchau.gitsample.di

import com.git.example.ntmchau.gitsample.api.ApiService
import com.git.example.ntmchau.gitsample.util.BASE_URL
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, DatabaseModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): ApiService {
        ServiceGenerator.setBaseUrl(BASE_URL)
        return ServiceGenerator.createService(ApiService::class.java)
    }
}
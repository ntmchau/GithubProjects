package com.git.example.ntmchau.gitsample.di

import android.app.Application
import androidx.room.Room
import com.git.example.ntmchau.gitsample.database.GithubDatabase
import com.git.example.ntmchau.gitsample.database.RepositoryDao
import com.git.example.ntmchau.gitsample.util.GITHUB_DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule{
    @Singleton
    @Provides
    fun provideGithubDatabase(app: Application): GithubDatabase {
        return Room
            .databaseBuilder(app, GithubDatabase::class.java, GITHUB_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepositoryDao(db: GithubDatabase): RepositoryDao {
        return db.repoDao()
    }
}
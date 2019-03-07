package com.git.example.ntmchau.gitsample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.git.example.ntmchau.gitsample.data.local.Repo
import com.git.example.ntmchau.gitsample.data.local.Owner
import com.git.example.ntmchau.gitsample.database.converters.DateConverter
import com.git.example.ntmchau.gitsample.util.GITHUB_DB_VER

@Database(
    entities = [
        Owner::class,
        Repo::class],
    version = GITHUB_DB_VER,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun repoDao(): RepositoryDao
}
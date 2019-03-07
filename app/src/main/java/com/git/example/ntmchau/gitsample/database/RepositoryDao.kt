package com.git.example.ntmchau.gitsample.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.git.example.ntmchau.gitsample.data.local.Owner
import com.git.example.ntmchau.gitsample.data.local.Repo
import com.git.example.ntmchau.gitsample.data.local.RepoExt

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repositories: List<Repo>)

    @Query("SELECT * FROM repository WHERE ownerLogin = :ownerLogin AND name = :name")
    fun load(ownerLogin: String, name: String): LiveData<Repo>

    @Query("SELECT * FROM repository, owner WHERE ownerLogin = :ownerLogin AND ownerLogin = owner.login")
    fun getRepositories(ownerLogin: String) : DataSource.Factory<Int, RepoExt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwners(owners: List<Owner>)

}
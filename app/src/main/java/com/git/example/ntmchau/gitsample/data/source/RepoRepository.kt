package com.git.example.ntmchau.gitsample.data.source

import android.util.Log
import androidx.annotation.MainThread
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.git.example.ntmchau.gitsample.api.ApiService
import com.git.example.ntmchau.gitsample.data.local.Owner
import com.git.example.ntmchau.gitsample.data.local.Repo
import com.git.example.ntmchau.gitsample.data.local.RepoExt
import com.git.example.ntmchau.gitsample.data.remote.RepoRemote
import com.git.example.ntmchau.gitsample.database.RepositoryDao
import com.git.example.ntmchau.gitsample.ui.repositories.RepositoryBoundaryCallback
import com.git.example.ntmchau.gitsample.util.*
import javax.inject.Inject

class RepoRepository @Inject constructor(private val mGithubService: ApiService,
                                         private val mRepositoryDao: RepositoryDao) {

    suspend fun fetchRepositories(login: String, pageOffset: Int, pageSize: Int): RepoResponse<List<RepoRemote>> {
        val response = mGithubService.getRepos(login, pageOffset, pageSize).await()

        when (response) {
            is Success -> {
                if (response.response != null) {
                    try {
                        val repoList = mutableListOf<Repo>()
                        val owners = mutableListOf<Owner>()
                        response.response.forEach {
                            repoList.add(it.toRepo())
                            owners.add(it.owner.toOwner())
                        }
                        mRepositoryDao.insertOwners(owners)
                        mRepositoryDao.insertRepos(repoList)
                    } catch (exception: Exception) {
                        Log.d(TAG, "fetchRepositories exception=${exception.printStackTrace()}")
                    }
                }
            }
            is Failure -> {
                Log.d(TAG, "fetchRepositories Failure code=${response.code}")
            }
        }
        return response
    }

    @MainThread
    fun getRepositories(loginName: String, pageSize: Int, appExecutors: AppExecutors): Listing<RepoExt> {
        Log.d(TAG, "getRepositories")
        val boundaryCallback = RepositoryBoundaryCallback(
            this,
            appExecutors,
            loginName,
            pageSize
        )
        // create a data source factory from Room
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(pageSize)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()
        val dataSourceFactory = mRepositoryDao.getRepositories(loginName)
        val builder = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)

        return Listing(
            pagedList = builder.build(),
            networkState = boundaryCallback.mNetworkState,
            retry = {
                boundaryCallback.mHelper.retryAllFailed()
            }
        )
    }

    companion object {
        private val TAG = RepoRepository::class.java.simpleName
    }
}
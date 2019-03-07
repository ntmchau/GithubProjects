package com.git.example.ntmchau.gitsample.ui.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagingRequestHelper
import com.git.example.ntmchau.gitsample.data.local.RepoExt
import com.git.example.ntmchau.gitsample.data.source.NetworkState
import com.git.example.ntmchau.gitsample.data.source.RepoRepository
import com.git.example.ntmchau.gitsample.util.AppExecutors
import com.git.example.ntmchau.gitsample.util.Failure
import com.git.example.ntmchau.gitsample.util.Success
import com.git.example.ntmchau.gitsample.util.createStatusLiveData
import kotlinx.coroutines.experimental.launch
import java.net.HttpURLConnection

class RepositoryBoundaryCallback constructor(private val mRepoRepository: RepoRepository,
                                             appExecutors: AppExecutors,
                                             private val mLoginName: String,
                                             private val mPageSize: Int) : PagedList.BoundaryCallback<RepoExt>() {

    var mHelper: PagingRequestHelper = PagingRequestHelper(appExecutors.diskIO())
    var mNetworkState: LiveData<NetworkState>
    private var mPageOffset: Int = 1

    init {
        mNetworkState = mHelper.createStatusLiveData()
    }

    companion object {
        private val TAG = RepositoryBoundaryCallback::class.java.simpleName
    }

    override fun onZeroItemsLoaded() {
        Log.d(TAG, "onZeroItemsLoaded")
        mHelper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {callback ->
            loadData(callback, mLoginName, mPageOffset, mPageSize)
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: RepoExt) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.d(TAG, "onItemAtEndLoaded: $itemAtEnd")
        mHelper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {callback ->
            mPageOffset++
            loadData(callback, mLoginName, mPageOffset, mPageSize)
        }
    }

    private fun loadData(callback: PagingRequestHelper.Request.Callback, login: String, pageOffset: Int, pageSize: Int) {
        launch {
            val data = mRepoRepository.fetchRepositories(login, pageOffset, pageSize)
            when (data) {
                is Success -> callback.recordSuccess()
                is Failure -> {
                    if (data.throwable != null) {
                        callback.recordFailure(data.throwable)
                    } else if (data.code >= HttpURLConnection.HTTP_OK && data.code < HttpURLConnection.HTTP_MULT_CHOICE) {
                        callback.recordFailure(Throwable(data.code.toString()))
                    }
                }
            }
        }
    }
}
package com.git.example.ntmchau.gitsample.ui.repositories

import androidx.lifecycle.ViewModel
import com.git.example.ntmchau.gitsample.data.source.RepoRepository
import com.git.example.ntmchau.gitsample.ui.common.RetryCallback
import com.git.example.ntmchau.gitsample.util.AppExecutors
import com.git.example.ntmchau.gitsample.util.LOGIN_NAME
import com.git.example.ntmchau.gitsample.util.PAGE_LIMIT
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(mRepoRepository: RepoRepository, mAppExecutors: AppExecutors) : ViewModel() {

    private val repositoriesResult = mRepoRepository.getRepositories(LOGIN_NAME, PAGE_LIMIT, mAppExecutors)

    val repositories = repositoriesResult.pagedList
    val networkState = repositoriesResult.networkState
    val retry = object: RetryCallback {
        override fun retry() {
            repositoriesResult.retry.invoke()
        }

    }
}
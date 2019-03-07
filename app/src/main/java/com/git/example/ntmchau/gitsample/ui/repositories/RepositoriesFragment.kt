package com.git.example.ntmchau.gitsample.ui.repositories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.git.example.ntmchau.gitsample.R
import com.git.example.ntmchau.gitsample.binding.FragmentDataBindingComponent
import com.git.example.ntmchau.gitsample.data.local.RepoExt
import com.git.example.ntmchau.gitsample.databinding.FragmentRepositoriesBinding
import com.git.example.ntmchau.gitsample.di.Injectable
import com.git.example.ntmchau.gitsample.ui.repositories.adapter.RepositoriesAdapter
import com.git.example.ntmchau.gitsample.util.autoCleared
import javax.inject.Inject

class RepositoriesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoViewModel: RepositoryViewModel

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<FragmentRepositoriesBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repoViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RepositoryViewModel::class.java)
        initAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentRepositoriesBinding>(
            inflater,
            R.layout.fragment_repositories,
            container,
            false
        )
        binding = dataBinding
        return dataBinding.root
    }

    private fun initAdapter() {
        val adapter = RepositoriesAdapter(repoViewModel.retry, dataBindingComponent) { repo ->
            navController().navigate(RepositoriesFragmentDirections.showDetails(repo.owner.avatarUrl,
                repo.owner.login, repo.repo.description?:""))
        }
        binding.list.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = adapter
        }
        repoViewModel.repositories.observe(this, Observer<PagedList<RepoExt>> {
            Log.d(TAG, "submit list size=${it?.size}")
            adapter.submitList(it)
        })
        repoViewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
    }

    private fun navController() = findNavController()

    companion object {
        private val TAG = RepositoriesFragment::class.java.simpleName
    }

}
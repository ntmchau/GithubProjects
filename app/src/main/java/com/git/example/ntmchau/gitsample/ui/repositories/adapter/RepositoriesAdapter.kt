package com.git.example.ntmchau.gitsample.ui.repositories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.git.example.ntmchau.gitsample.R
import com.git.example.ntmchau.gitsample.data.local.RepoExt
import com.git.example.ntmchau.gitsample.data.source.NetworkState
import com.git.example.ntmchau.gitsample.databinding.NetworkStateItemBinding
import com.git.example.ntmchau.gitsample.databinding.RepositoryItemBinding
import com.git.example.ntmchau.gitsample.ui.common.DataBoundViewHolder
import com.git.example.ntmchau.gitsample.ui.common.RetryCallback

class RepositoriesAdapter(
    private val mRetryCallback: RetryCallback,
    private val dataBindingComponent: DataBindingComponent,
    private val callback: ((RepoExt) -> Unit)?
) : PagedListAdapter<RepoExt, RecyclerView.ViewHolder>(REPOSITORY_COMPARATOR) {

    private var mNetworkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.repository_item -> {
                (holder as DataBoundViewHolder<RepositoryItemBinding>).binding.repository = getItem(position)
                holder.binding.executePendingBindings()
            }
            R.layout.network_state_item -> {
                (holder as DataBoundViewHolder<NetworkStateItemBinding>).binding.networkState = mNetworkState
                holder.binding.executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.repository_item -> {
                val binding = DataBindingUtil.inflate<RepositoryItemBinding>(
                    LayoutInflater.from(parent.context), R.layout.repository_item, parent, false, dataBindingComponent)
                binding.root.setOnClickListener {
                    binding.repository?.let {repo ->
                        callback?.invoke(repo)
                    }
                }
                DataBoundViewHolder(binding)
            }
            R.layout.network_state_item -> {
                val binding = DataBindingUtil.inflate<NetworkStateItemBinding>(
                    LayoutInflater.from(parent.context), R.layout.network_state_item, parent, false, dataBindingComponent)
                binding.callback = mRetryCallback
                DataBoundViewHolder(binding)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = mNetworkState != null && mNetworkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.repository_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.mNetworkState
        val hadExtraRow = hasExtraRow()
        this.mNetworkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val REPOSITORY_COMPARATOR = object : DiffUtil.ItemCallback<RepoExt>() {
            override fun areContentsTheSame(oldItem: RepoExt, newItem: RepoExt): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: RepoExt, newItem: RepoExt): Boolean =
                oldItem.repo.name == newItem.repo.name && oldItem.owner.login == oldItem.owner.login
        }
    }
}
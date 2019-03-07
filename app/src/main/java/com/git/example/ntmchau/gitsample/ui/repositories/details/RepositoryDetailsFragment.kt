package com.git.example.ntmchau.gitsample.ui.repositories.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.git.example.ntmchau.gitsample.R
import com.git.example.ntmchau.gitsample.binding.FragmentDataBindingComponent
import com.git.example.ntmchau.gitsample.databinding.FragmentDetailsBinding
import com.git.example.ntmchau.gitsample.di.Injectable
import com.git.example.ntmchau.gitsample.util.autoCleared

class RepositoryDetailsFragment : Fragment(), Injectable {

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<FragmentDetailsBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = RepositoryDetailsFragmentArgs.fromBundle(arguments)
        binding.url = params.avatar
        binding.name = params.login
        binding.description = params.description
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentDetailsBinding>(
            inflater,
            R.layout.fragment_details,
            container,
            false, dataBindingComponent)
        binding = dataBinding
        return dataBinding.root
    }
}
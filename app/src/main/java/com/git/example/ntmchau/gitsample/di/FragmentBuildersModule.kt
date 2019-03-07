package com.git.example.ntmchau.gitsample.di

import com.git.example.ntmchau.gitsample.ui.repositories.RepositoriesFragment
import com.git.example.ntmchau.gitsample.ui.repositories.details.RepositoryDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepositoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeRepositoryDetailsFragment(): RepositoryDetailsFragment
}
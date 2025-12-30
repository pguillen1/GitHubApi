package com.pguillen.githubapi.di

import com.pguillen.githubapi.domain.usercase.getuserrepos.GetUserRepos
import com.pguillen.githubapi.domain.usercase.getuserrepos.GetUserReposImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetUserRepos(
        impl: GetUserReposImpl
    ): GetUserRepos
}
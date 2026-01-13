package com.pguillen.githubapi.di

import com.pguillen.githubapi.domain.usercase.getrepodetail.GetRepoDetail
import com.pguillen.githubapi.domain.usercase.getrepodetail.GetRepoDetailImpl
import com.pguillen.githubapi.domain.usercase.getuserrepos.GetUserRepos
import com.pguillen.githubapi.domain.usercase.getuserrepos.GetUserReposImpl
import com.pguillen.githubapi.domain.usercase.observeuserrepos.ObserveUserRepos
import com.pguillen.githubapi.domain.usercase.observeuserrepos.ObserveUserReposImpl
import com.pguillen.githubapi.domain.usercase.refreshuserrepos.RefreshUserRepos
import com.pguillen.githubapi.domain.usercase.refreshuserrepos.RefreshUserReposImpl
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

	@Binds
	abstract fun bindGetRepoDetail(
		impl: GetRepoDetailImpl
	): GetRepoDetail

	@Binds
	abstract fun bindObserveUserRepos(
		impl: ObserveUserReposImpl
	): ObserveUserRepos

	@Binds
	abstract fun bindRefreshUserRepos(
		impl: RefreshUserReposImpl
	): RefreshUserRepos
}
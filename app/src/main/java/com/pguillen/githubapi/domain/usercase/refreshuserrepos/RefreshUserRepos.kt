package com.pguillen.githubapi.domain.usercase.refreshuserrepos

interface RefreshUserRepos {
	suspend operator fun invoke(username: String)
}
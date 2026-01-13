package com.pguillen.githubapi.domain.usercase.refreshuserrepos

import com.pguillen.githubapi.domain.repository.GitHubRepository
import javax.inject.Inject

class RefreshUserReposImpl @Inject constructor(private val repository: GitHubRepository) :
	RefreshUserRepos {
	override suspend fun invoke(username: String) {
		repository.refreshUserRepos(username)
	}
}
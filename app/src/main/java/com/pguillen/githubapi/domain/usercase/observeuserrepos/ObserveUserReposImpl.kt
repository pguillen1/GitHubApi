package com.pguillen.githubapi.domain.usercase.observeuserrepos

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserReposImpl @Inject constructor(private val repository: GitHubRepository) :
	ObserveUserRepos {
	override fun invoke(username: String): Flow<List<RepoDomain>> {
		return repository.observeUserRepos(username)
	}
}
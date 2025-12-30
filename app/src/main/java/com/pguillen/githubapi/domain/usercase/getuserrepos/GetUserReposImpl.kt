package com.pguillen.githubapi.domain.usercase.getuserrepos

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserReposImpl @Inject constructor(
	private val repository: GitHubRepository
): GetUserRepos {
	override suspend operator fun invoke(username: String): List<RepoDomain> {
		return repository.getUserRepos(username)
	}
}
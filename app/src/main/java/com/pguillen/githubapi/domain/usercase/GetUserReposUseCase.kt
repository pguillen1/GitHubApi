package com.pguillen.githubapi.domain.usercase

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
	private val repository: GitHubRepository
) {
	suspend operator fun invoke(username: String): List<RepoDomain> {
		return repository.getUserRepos(username)
	}
}
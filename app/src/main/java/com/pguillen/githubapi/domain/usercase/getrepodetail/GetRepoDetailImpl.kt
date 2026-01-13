package com.pguillen.githubapi.domain.usercase.getrepodetail

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository
import javax.inject.Inject

class GetRepoDetailImpl @Inject constructor(
	private val repository: GitHubRepository
) : GetRepoDetail {
	override suspend fun invoke(
		owner: String,
		repoName: String
	): RepoDomain {
		return repository.getRepoDetail(owner, repoName)
	}
}
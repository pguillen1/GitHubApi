package com.pguillen.githubapi.fakes

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository

class FakeRepository : GitHubRepository {

	var shouldThrowError = false
	var repos: List<RepoDomain> = emptyList()
	var repoDetail: RepoDomain? = null

	override suspend fun getUserRepos(username: String): List<RepoDomain> {
		if (shouldThrowError) {
			throw RuntimeException("Error")
		}
		return repos
	}

	override suspend fun getRepoDetail(
		owner: String,
		repoName: String
	): RepoDomain {
		if (shouldThrowError) {
			throw RuntimeException("Error")
		}
		return repoDetail!!
	}
}
package com.pguillen.githubapi.data.remote.repository

import com.pguillen.githubapi.data.remote.api.GitHubApi
import com.pguillen.githubapi.data.remote.dto.toDomain
import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
	private val api: GitHubApi
) : GitHubRepository {
	override suspend fun getUserRepos(username: String): List<RepoDomain> {
		return api.getUserRepos(username).map {
			it.toDomain()
		}
	}
}
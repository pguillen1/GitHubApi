package com.pguillen.githubapi.domain.repository

import com.pguillen.githubapi.domain.model.RepoDomain
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
	suspend fun getUserRepos(username: String): List<RepoDomain>
	fun observeUserRepos(username: String): Flow<List<RepoDomain>>
	suspend fun refreshUserRepos(username: String)
	suspend fun getRepoDetail(owner: String, repoName: String): RepoDomain
}
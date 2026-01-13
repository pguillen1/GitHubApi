package com.pguillen.githubapi.data.remote.repository

import com.pguillen.githubapi.data.local.entity.RepoDao
import com.pguillen.githubapi.data.local.entity.toDomain
import com.pguillen.githubapi.data.remote.api.GitHubApi
import com.pguillen.githubapi.data.remote.dto.toDomain
import com.pguillen.githubapi.data.remote.dto.toEntity
import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
	private val api: GitHubApi,
	private val repoDao: RepoDao
) : GitHubRepository {
	override suspend fun getUserRepos(username: String): List<RepoDomain> {
		return api.getUserRepos(username).map {
			it.toDomain()
		}
	}

	override fun observeUserRepos(username: String): Flow<List<RepoDomain>> {
		return repoDao.observeRepoByOwner(username).map { entities ->
			entities.map {
				it.toDomain()
			}
		}
	}

	override suspend fun refreshUserRepos(username: String) {
		val repos = api.getUserRepos(username).map {
			it.toEntity(username)
		}
		repoDao.replaceReposForUser(username, repos)
	}

	override suspend fun getRepoDetail(
		owner: String,
		repoName: String
	): RepoDomain {
		return api.getRepoDetail(owner, repoName).toDomain()
	}
}
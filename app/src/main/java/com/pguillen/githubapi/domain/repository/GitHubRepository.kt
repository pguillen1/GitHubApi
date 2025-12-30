package com.pguillen.githubapi.domain.repository

import com.pguillen.githubapi.domain.model.RepoDomain

interface GitHubRepository {
	suspend fun getUserRepos(username: String): List<RepoDomain>
}
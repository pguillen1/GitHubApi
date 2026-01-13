package com.pguillen.githubapi.domain.usercase.observeuserrepos

import com.pguillen.githubapi.domain.model.RepoDomain
import kotlinx.coroutines.flow.Flow

interface ObserveUserRepos {
	operator fun invoke(username: String): Flow<List<RepoDomain>>
}
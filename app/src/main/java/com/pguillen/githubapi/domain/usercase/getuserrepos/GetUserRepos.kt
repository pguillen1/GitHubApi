package com.pguillen.githubapi.domain.usercase.getuserrepos

import com.pguillen.githubapi.domain.model.RepoDomain

interface GetUserRepos {
    suspend operator fun invoke(username: String): List<RepoDomain>
}
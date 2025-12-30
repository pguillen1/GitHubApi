package com.pguillen.githubapi.fakes

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.usercase.getuserrepos.GetUserRepos

class FakeGetUserRepos: GetUserRepos {

	var repos: List<RepoDomain> = emptyList()
    var shouldThrowError = false

    override suspend fun invoke(username: String): List<RepoDomain> {
        if (shouldThrowError) {
            throw RuntimeException("Error")
        }
        return repos
    }
}
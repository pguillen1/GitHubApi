package com.pguillen.githubapi.fakes

import com.pguillen.githubapi.domain.usercase.refreshuserrepos.RefreshUserRepos

class FakeRefreshUserRepos: RefreshUserRepos {

    var shouldThrowError = false
    val calls = mutableListOf<String>()

    override suspend fun invoke(username: String) {
        calls += username
        if (shouldThrowError) throw RuntimeException("Error")
    }
}
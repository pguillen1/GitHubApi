package com.pguillen.githubapi.fakes

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRepository : GitHubRepository {

    var shouldThrowError = false
    var shouldThrowErrorObserve = false
    var shouldThrowErrorRefresh = false
    var shouldThrowErrorGetRepoDetail = false
    var repos: List<RepoDomain> = emptyList()
    var repoDetail: RepoDomain? = null
    private val cacheFlows = mutableMapOf<String, MutableStateFlow<List<RepoDomain>>>()
    private val remoteRepos = mutableMapOf<String, List<RepoDomain>>()
    val refreshCalls = mutableListOf<String>()

    fun setRemoteRepos(username: String, repos: List<RepoDomain>) {
        remoteRepos[username] = repos
    }

    fun seedCache(username: String, repos: List<RepoDomain>) {
        flowFor(username).value = repos
    }

    private fun flowFor(username: String): MutableStateFlow<List<RepoDomain>> {
        return cacheFlows.getOrPut(username) { MutableStateFlow(emptyList()) }
    }

    override suspend fun getUserRepos(username: String): List<RepoDomain> {
        if (shouldThrowError) {
            throw RuntimeException("Error")
        }
        return repos
    }

    override fun observeUserRepos(username: String): Flow<List<RepoDomain>> {
        if (shouldThrowErrorObserve) {
            throw RuntimeException("Error")
        }

        return flowFor(username)
    }

    override suspend fun refreshUserRepos(username: String) {
        if (shouldThrowErrorRefresh) {
            throw RuntimeException("Error")
        }
        refreshCalls += username
        val newRepos = remoteRepos[username].orEmpty()
        flowFor(username).value = newRepos
    }

    override suspend fun getRepoDetail(
        owner: String,
        repoName: String
    ): RepoDomain {
        if (shouldThrowErrorGetRepoDetail) {
            throw RuntimeException("Error")
        }
        return repoDetail!!
    }
}
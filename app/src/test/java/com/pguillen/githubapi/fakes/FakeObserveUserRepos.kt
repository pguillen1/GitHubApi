package com.pguillen.githubapi.fakes

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.usercase.observeuserrepos.ObserveUserRepos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeObserveUserRepos : ObserveUserRepos {

    private val flows = mutableMapOf<String, MutableStateFlow<List<RepoDomain>>>()
    fun flowFor(username: String): MutableStateFlow<List<RepoDomain>> {
        return flows.getOrPut(username) { MutableStateFlow(emptyList()) }
    }

    override fun invoke(username: String): Flow<List<RepoDomain>> = flowFor(username)

}
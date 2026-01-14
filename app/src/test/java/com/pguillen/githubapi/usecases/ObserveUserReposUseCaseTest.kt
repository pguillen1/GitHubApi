package com.pguillen.githubapi.usecases

import com.pguillen.githubapi.createListRepoDomainOfOwner
import com.pguillen.githubapi.domain.usercase.observeuserrepos.ObserveUserRepos
import com.pguillen.githubapi.domain.usercase.observeuserrepos.ObserveUserReposImpl
import com.pguillen.githubapi.fakes.FakeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class ObserveUserReposUseCaseTest {

    private lateinit var repository: FakeRepository
    private lateinit var observeUserRepos: ObserveUserRepos

    @Before
    fun setup() {
        repository = FakeRepository()
        observeUserRepos = ObserveUserReposImpl(repository)
    }

    @Test
    fun observeUserRepos_returns_flow_from_repository() = runTest {
        val username = "Test"
        val repos = createListRepoDomainOfOwner(username)
        repository.seedCache(username, repos)
        val result = observeUserRepos(username).first()
        assertEquals(repos, result)
    }

    @Test
    fun observeUserRepos_propagates_error_when_repository_throws() = runTest {
        repository.shouldThrowErrorObserve = true
        val username = "Test"
        assertFailsWith<RuntimeException> {
            observeUserRepos(username).first()
        }
    }
}
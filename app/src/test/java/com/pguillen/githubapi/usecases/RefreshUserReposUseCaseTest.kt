package com.pguillen.githubapi.usecases

import com.pguillen.githubapi.createListRepoDomainOfOwner
import com.pguillen.githubapi.domain.usercase.refreshuserrepos.RefreshUserRepos
import com.pguillen.githubapi.domain.usercase.refreshuserrepos.RefreshUserReposImpl
import com.pguillen.githubapi.fakes.FakeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RefreshUserReposUseCaseTest {

    private lateinit var repository: FakeRepository
    private lateinit var refreshUserRepos: RefreshUserRepos

    @Before
    fun setup() {
        repository = FakeRepository()
        refreshUserRepos = RefreshUserReposImpl(repository)
    }

    @Test
    fun refreshUserRepos_calls_repository_with_correct_username() = runTest {
        val username = "test"
        refreshUserRepos(username)
        assertEquals(listOf(username), repository.refreshCalls)
    }

    @Test
    fun refreshUserRepos_propagates_error_when_repository_throws() = runTest {
        val username = "test"
        repository.shouldThrowErrorRefresh = true
        assertFailsWith<RuntimeException> {
            refreshUserRepos(username)
        }
    }

    @Test
    fun refreshUserRepos_updates_cached_flow_in_fake_repository() = runTest {
        val username = "test"
        val remoteRepos = createListRepoDomainOfOwner(username)
        repository.setRemoteRepos(username, remoteRepos)
        refreshUserRepos(username)

        val cached = repository.observeUserRepos(username).first()
        assertEquals(remoteRepos, cached)
    }
}
package com.pguillen.githubapi.usecases

import com.pguillen.githubapi.createRepoDomainById
import com.pguillen.githubapi.domain.usercase.getrepodetail.GetRepoDetail
import com.pguillen.githubapi.domain.usercase.getrepodetail.GetRepoDetailImpl
import com.pguillen.githubapi.fakes.FakeRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class GetRepoDetailUseCaseTest {

	private lateinit var repository: FakeRepository
	private lateinit var getRepoDetail: GetRepoDetail

	@Before
	fun setup() {
		repository = FakeRepository()
		getRepoDetail = GetRepoDetailImpl(repository)
	}

	@Test
	fun when_invoked_returns_repo_detail_from_repository() = runTest {
		val repo = createRepoDomainById(1)
		repository.repoDetail = repo
		assertEquals(repo, getRepoDetail.invoke(repo.ownerName, repo.name))
	}

	@Test
	fun returns_error_when_fails() = runTest {
		repository.shouldThrowError = true
		try {
			getRepoDetail("test", "test")
			fail("Expected RuntimeException")
		}
		catch (e: Exception) {
			assertEquals("Error", e.message)
		}
	}
}
package com.pguillen.githubapi.usecases

import com.pguillen.githubapi.createRepoDomain
import com.pguillen.githubapi.domain.usercase.GetUserReposUseCase
import com.pguillen.githubapi.fakes.FakeRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


class GetUserReposUseCaseTest() {

	private lateinit var fakeRepository: FakeRepository
	private lateinit var getUserReposUseCase: GetUserReposUseCase

	@Before
	fun setup() {
		fakeRepository = FakeRepository()
		getUserReposUseCase = GetUserReposUseCase(fakeRepository)
	}

	@Test
	fun returns_empty_list_when_repository_returns_empty_list() = runTest {
		val testUser = "Test username"
		val returnList = getUserReposUseCase(testUser)
		assertEquals(fakeRepository.repos, returnList)
	}

	@Test
	fun returns_repos_when_repository_returns_data() = runTest {
		val testUser = "Test username"
		fakeRepository.repos =
			listOf(createRepoDomain(1))
		val returnList = getUserReposUseCase(testUser)
		assertEquals(fakeRepository.repos, returnList)
	}

	@Test
	fun returns_error_when_repository_returns_error() = runTest {
		val testUser = "Test username"
		fakeRepository.shouldThrowError = true
		try {
			getUserReposUseCase(testUser)
			fail("Expected RuntimeException")
		}
		catch (e: Exception) {
			assertEquals("Error", e.message)
		}
	}
}
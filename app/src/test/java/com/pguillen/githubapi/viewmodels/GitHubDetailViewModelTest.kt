package com.pguillen.githubapi.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.pguillen.githubapi.createRepoDomainByOwnerName
import com.pguillen.githubapi.fakes.FakeGetRepoDetail
import com.pguillen.githubapi.ui.screen.github_detail.GitHubDetailEvent
import com.pguillen.githubapi.ui.screen.github_detail.GitHubDetailState
import com.pguillen.githubapi.ui.screen.github_detail.GitHubDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GitHubDetailViewModelTest {

	private val testDispatcher = StandardTestDispatcher()
	private lateinit var getRepoDetail: FakeGetRepoDetail
	private lateinit var viewModel: GitHubDetailViewModel
	private lateinit var savedStateHandle: SavedStateHandle

	@OptIn(ExperimentalCoroutinesApi::class)
	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		getRepoDetail = FakeGetRepoDetail()
		//savedStateHandle = SavedStateHandle()
		//viewModel = GitHubDetailViewModel(savedStateHandle, getRepoDetail)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun when_initialized_loads_repo_and_emits_success() = runTest {
		getRepoDetail.repoDetail = createRepoDomainByOwnerName(
			owner = "google",
			repoName = "guava"
		)
		savedStateHandle = SavedStateHandle(
			mapOf(
				"owner" to "google",
				"repoName" to "guava"
			)
		)
		viewModel = GitHubDetailViewModel(savedStateHandle, getRepoDetail)
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals("google", (viewModel.uiState.value as GitHubDetailState.Success).repo.owner)
		assertEquals("guava", (viewModel.uiState.value as GitHubDetailState.Success).repo.name)
	}

	@Test
	fun when_use_case_throws_emits_error() = runTest {
		getRepoDetail.shouldThrowError = true
		savedStateHandle = SavedStateHandle(
			mapOf(
				"owner" to "google",
				"repoName" to "guava"
			)
		)
		viewModel = GitHubDetailViewModel(savedStateHandle, getRepoDetail)
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals(GitHubDetailState.Error("Ha habido un error al cargar el repo."), viewModel.uiState.value)
	}

	@Test
	fun retry_reloads_repo_after_error() = runTest {
		getRepoDetail.shouldThrowError = true
		getRepoDetail.repoDetail = createRepoDomainByOwnerName(
			owner = "google",
			repoName = "guava"
		)
		savedStateHandle = SavedStateHandle(
			mapOf(
				"owner" to "google",
				"repoName" to "guava"
			)
		)
		viewModel = GitHubDetailViewModel(savedStateHandle, getRepoDetail)
		testDispatcher.scheduler.advanceUntilIdle()
		getRepoDetail.shouldThrowError = false
		viewModel.onEvent(GitHubDetailEvent.OnRetry)
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals("google", (viewModel.uiState.value as GitHubDetailState.Success).repo.owner)
		assertEquals("guava", (viewModel.uiState.value as GitHubDetailState.Success).repo.name)
	}
}

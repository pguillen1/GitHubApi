package com.pguillen.githubapi.viewmodels

import com.pguillen.githubapi.createListRepoDomainOfOwner
import com.pguillen.githubapi.domain.model.toUi
import com.pguillen.githubapi.fakes.FakeObserveUserRepos
import com.pguillen.githubapi.fakes.FakeRefreshUserRepos
import com.pguillen.githubapi.ui.screen.github_list.GitHubListUiEffect
import com.pguillen.githubapi.ui.screen.github_list.GitHubListUiEvent
import com.pguillen.githubapi.ui.screen.github_list.GitHubListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

class GitHubListViewModelTest {

	private val testDispatcher = StandardTestDispatcher()
	private lateinit var fakeObserveUserRepos: FakeObserveUserRepos
	private lateinit var fakeRefreshUserRepos: FakeRefreshUserRepos
	private lateinit var viewModel: GitHubListViewModel

	@OptIn(ExperimentalCoroutinesApi::class)
	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		fakeObserveUserRepos = FakeObserveUserRepos()
		fakeRefreshUserRepos = FakeRefreshUserRepos()
		viewModel = GitHubListViewModel(fakeObserveUserRepos, fakeRefreshUserRepos)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun check_initial_values_of_viewmodel() {
		assertEquals("", viewModel.uiState.value.query)
		assertFalse(viewModel.uiState.value.isLoading)
		assertNull(viewModel.uiState.value.error)
		assertTrue(viewModel.uiState.value.repos.isEmpty())
	}

	@Test
	fun onTextChange_updates_query() {
		viewModel.onEvent(GitHubListUiEvent.OnTextChange("Test"))
		assertEquals("Test", viewModel.uiState.value.query)
	}

	@Test
	fun onRepoClick_emits_NavigateToDetail() = runTest {
		viewModel.onEvent(GitHubListUiEvent.OnRepoClick("owner", "repo"))
		val effect = viewModel.uiEffect.first()
		assertEquals(GitHubListUiEffect.NavigateToRepoDetail("owner", "repo"), effect)
	}

	@Test
	fun onSearchClick_emits_snackbar_when_query_is_blank() = runTest {
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		val effect = viewModel.uiEffect.first()
		assertTrue(effect is GitHubListUiEffect.ShowSnackbar)
		assertTrue(fakeRefreshUserRepos.calls.isEmpty())
	}

	@Test
	fun onSearchClick_calls_refresh_with_a_valid_username() = runTest {
		val username = "test"
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(username))
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		assertTrue(viewModel.uiState.value.isLoading)
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals(listOf(username), fakeRefreshUserRepos.calls)
		assertFalse(viewModel.uiState.value.isLoading)
	}

	@Test
	fun onSearchClick_starts_observation_and_refresh_repos() = runTest {
		val username = "test"
		val repos = createListRepoDomainOfOwner(username)
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(username))
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		fakeObserveUserRepos.flowFor(username).value = repos
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals(repos.map { it.toUi() }, viewModel.uiState.value.repos)
		assertNull(viewModel.uiState.value.error)
	}

	@Test
	fun refresh_returns_an_error() = runTest {
		val username = "test"
		fakeRefreshUserRepos.shouldThrowError = true
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(username))
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		testDispatcher.scheduler.advanceUntilIdle()
		assertNotNull(viewModel.uiState.value.error)
	}

	@Test
	fun observe_returns_an_error() = runTest {
		val username = "test"
		fakeObserveUserRepos.shouldThrowError = true
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(username))
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		testDispatcher.scheduler.advanceUntilIdle()
		assertNotNull(viewModel.uiState.value.error)
	}

	@Test
	fun onRetry_emits_snackbar_before_any_search() = runTest {
		viewModel.onEvent(GitHubListUiEvent.OnRetry)
		val effect = viewModel.uiEffect.first()
		assertTrue(effect is GitHubListUiEffect.ShowSnackbar)
		assertTrue(fakeRefreshUserRepos.calls.isEmpty())
	}

	@Test
	fun onRetry_refresh_last_call_not_the_Actual_one() = runTest {
		val username = "test"
		val newUsername = "random"
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(username))
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		testDispatcher.scheduler.advanceUntilIdle()
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(newUsername))
		viewModel.onEvent(GitHubListUiEvent.OnRetry)
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals(listOf(username, username), fakeRefreshUserRepos.calls)
	}

	@Test
	fun new_search_cancels_previous_observation_flow() = runTest {
		val username = "test"
		val newUsername = "random"
		val repos = createListRepoDomainOfOwner(username)
		val newRepos = createListRepoDomainOfOwner(newUsername)
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(username))
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		testDispatcher.scheduler.advanceUntilIdle()
		viewModel.onEvent(GitHubListUiEvent.OnTextChange(newUsername))
		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
		testDispatcher.scheduler.advanceUntilIdle()
		fakeObserveUserRepos.flowFor(newUsername).value = newRepos
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals(newRepos.map { it.toUi() }, viewModel.uiState.value.repos)
		fakeObserveUserRepos.flowFor(username).value = repos
		testDispatcher.scheduler.advanceUntilIdle()
		assertEquals(newRepos.map { it.toUi() }, viewModel.uiState.value.repos)
	}
}
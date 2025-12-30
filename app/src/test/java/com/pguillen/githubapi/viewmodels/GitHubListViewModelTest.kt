package com.pguillen.githubapi.viewmodels

import com.pguillen.githubapi.fakes.FakeGetUserReposUseCase
import com.pguillen.githubapi.ui.screen.github_list.GitHubListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

class GitHubListViewModelTest {

	private val testDispatcher = StandardTestDispatcher()
	private lateinit var fakeGetUserReposUseCase: FakeGetUserReposUseCase
	private lateinit var viewModel: GitHubListViewModel

	@OptIn(ExperimentalCoroutinesApi::class)
	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		fakeGetUserReposUseCase = FakeGetUserReposUseCase()
		viewModel = GitHubListViewModel(fakeGetUserReposUseCase)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
}
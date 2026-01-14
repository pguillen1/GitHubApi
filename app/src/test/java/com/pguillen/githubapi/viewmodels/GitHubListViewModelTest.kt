package com.pguillen.githubapi.viewmodels

class GitHubListViewModelTest {

//	private val testDispatcher = StandardTestDispatcher()
//	private lateinit var fakeGetUserRepos: FakeGetUserRepos
//	private lateinit var viewModel: GitHubListViewModel
//
//	@OptIn(ExperimentalCoroutinesApi::class)
//	@Before
//	fun setup() {
//		Dispatchers.setMain(testDispatcher)
//		fakeGetUserRepos = FakeGetUserRepos()
//		viewModel = GitHubListViewModel(fakeGetUserRepos)
//	}
//
//	@OptIn(ExperimentalCoroutinesApi::class)
//	@After
//	fun tearDown() {
//		Dispatchers.resetMain()
//	}
//
//	@Test
//	fun check_initial_values_of_viewmodel() {
//        assertEquals("", viewModel.uiState.value.query)
//		assertFalse(viewModel.uiState.value.isLoading)
//        assertNull(viewModel.uiState.value.error)
//        assertTrue(viewModel.uiState.value.repos.isEmpty())
//	}
//
//	@Test
//	fun transition_from_empty_list_to_loading_when_searching() = runTest {
//		viewModel.onEvent(GitHubListUiEvent.OnTextChange("Text"))
//		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
//		assertEquals(GitHubListUiState.Loading, viewModel.uiState.value)
//	}
//
//	@Test
//	fun transition_from_loading_to_success_when_repository_returns_data() = runTest {
//		fakeGetUserRepos.repos = listOf(createRepoDomainById(1))
//		val reposUi = fakeGetUserRepos.repos.map { it.toUi() }
//		viewModel.onEvent(GitHubListUiEvent.OnTextChange("Text"))
//		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
//		testDispatcher.scheduler.advanceUntilIdle()
//		assertEquals(GitHubListUiState.Success(reposUi), viewModel.uiState.value)
//	}
//
//	@Test
//	fun transition_from_loading_to_empty_list_when_repository_does_not_return_data() = runTest {
//		viewModel.onEvent(GitHubListUiEvent.OnTextChange("Text"))
//		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
//		assertEquals(GitHubListUiState.Loading, viewModel.uiState.value)
//		testDispatcher.scheduler.advanceUntilIdle()
//		assertEquals(GitHubListUiState.EmptyList, viewModel.uiState.value)
//	}
//
//	@Test
//	fun transition_from_loading_to_error_when_repository_returns_an_error() = runTest {
//		fakeGetUserRepos.shouldThrowError = true
//		viewModel.onEvent(GitHubListUiEvent.OnTextChange("Text"))
//		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
//		assertEquals(GitHubListUiState.Loading, viewModel.uiState.value)
//		testDispatcher.scheduler.advanceUntilIdle()
//		assertEquals(GitHubListUiState.Error("Error cargando repos."), viewModel.uiState.value)
//	}
//
//	@Test
//	fun change_text_when_typing_in_text_field() {
//		viewModel.onEvent(GitHubListUiEvent.OnTextChange("Test"))
//		assertEquals("Test", viewModel.currentText.value)
//	}
//
//	@Test
//	fun emits_error_effect_when_search_text_is_blank() = runTest {
//		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
//		val effect = viewModel.uiEffect.first()
//		assertEquals(GitHubListUiEffect.ShowSnackbar("Introduce un texto"), effect)
//	}
//
//	@Test
//	fun emits_error_effect_when_repository_returns_an_error() = runTest {
//		fakeGetUserRepos.shouldThrowError = true
//		viewModel.onEvent(GitHubListUiEvent.OnTextChange("Text"))
//		viewModel.onEvent(GitHubListUiEvent.OnSearchClick)
//		val effect = viewModel.uiEffect.first()
//		assertEquals(GitHubListUiEffect.ShowSnackbar("No se ha podido cargar los repos"), effect)
//	}
}
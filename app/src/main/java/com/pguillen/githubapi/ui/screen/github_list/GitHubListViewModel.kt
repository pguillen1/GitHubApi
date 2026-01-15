package com.pguillen.githubapi.ui.screen.github_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pguillen.githubapi.domain.model.toUi
import com.pguillen.githubapi.domain.usercase.observeuserrepos.ObserveUserRepos
import com.pguillen.githubapi.domain.usercase.refreshuserrepos.RefreshUserRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubListViewModel @Inject constructor(
	private val observeUserRepos: ObserveUserRepos,
	private val refreshUserRepos: RefreshUserRepos
) : ViewModel() {

	private val _uiState = MutableStateFlow(GitHubListUiState())
	val uiState = _uiState.asStateFlow()

	private val _uiEffect = MutableSharedFlow<GitHubListUiEffect>(
		replay = 0,
		extraBufferCapacity = 1,
		onBufferOverflow = BufferOverflow.DROP_OLDEST
	)
	val uiEffect = _uiEffect.asSharedFlow()

	private var observeJob: Job? = null
	private var lastUsername: String? = null

	fun onEvent(event: GitHubListUiEvent) {
		when (event) {

			is GitHubListUiEvent.OnTextChange -> {
				_uiState.update { it.copy(query = event.text) }
			}

			GitHubListUiEvent.OnSearchClick -> {
				search()
			}

			is GitHubListUiEvent.OnRepoClick -> {
				navigateToDetail(owner = event.owner, repoName = event.repoName)
			}

			GitHubListUiEvent.OnRetry -> {
				lastUsername?.let { refresh(it) } ?: emitError("Primero busca un usuario")
			}
		}
	}

	private fun search() {
		if (_uiState.value.query.isBlank()) {
			emitError("Introduce un usuario")
			return
		}
		lastUsername = _uiState.value.query
		startObserving(lastUsername!!)
		refresh(lastUsername!!)
	}

	private fun emitError(message: String) {
		viewModelScope.launch {
			_uiEffect.emit(GitHubListUiEffect.ShowSnackbar(message))
		}
	}

	private fun navigateToDetail(
		owner: String,
		repoName: String
	) {
		viewModelScope.launch {
			_uiEffect.emit(
				GitHubListUiEffect.NavigateToRepoDetail(
					owner = owner,
					repoName = repoName
				)
			)
		}
	}

	private fun startObserving(username: String) {

		observeJob?.cancel()

		observeJob = observeUserRepos(username)
			.onEach { repos ->
				_uiState.update {
					it.copy(
						error = null,
						repos = repos.map { repo -> repo.toUi() })
				}
			}
			.catch {
				_uiState.update { it.copy(error = "Error leyendo cache.") }
			}
			.launchIn(viewModelScope)
	}

	private fun refresh(username: String) {
		_uiState.update { it.copy(isLoading = true) }
		viewModelScope.launch {
			try {
				refreshUserRepos(username)
			}
			catch (e: Exception) {
				_uiState.update { it.copy(error = "Error refrescando datos.") }
			}
			finally {
				_uiState.update { it.copy(isLoading = false) }
			}
		}
	}
}
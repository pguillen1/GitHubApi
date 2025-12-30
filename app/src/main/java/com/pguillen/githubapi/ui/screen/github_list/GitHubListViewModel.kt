package com.pguillen.githubapi.ui.screen.github_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pguillen.githubapi.domain.model.toUi
import com.pguillen.githubapi.domain.usercase.GetUserReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubListViewModel @Inject constructor(
	private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<GitHubListUiState>(GitHubListUiState.EmptyList)
	val uiState = _uiState.asStateFlow()

	private val _uiEffect = MutableSharedFlow<GitHubListUiEffect>()
	val uiEffect = _uiEffect.asSharedFlow()

	var currentText = ""

	fun onEvent(event: GitHubListUiEvent) {
		when (event) {

			is GitHubListUiEvent.OnTextChange -> {
				currentText = event.text
			}

			GitHubListUiEvent.OnSearchClick -> {
				search()
			}
		}
	}

	private fun search() {
		if (currentText.isBlank()) {
			emitError("Introduce un texto")
			return
		}
		viewModelScope.launch {
			try {
				val repos = getUserReposUseCase(currentText).map {
					it.toUi()
				}
				if (repos.isEmpty()) {
					_uiState.value = GitHubListUiState.EmptyList
				}
				else {
					_uiState.value = GitHubListUiState.Success(repos)
				}
			}
			catch (e: Exception) {
				_uiState.value = GitHubListUiState.Error("Error cargando repos.")
				emitError("No se ha podido cargar los repos")
			}
		}
	}

	private fun emitError(message: String) {
		viewModelScope.launch {
			_uiEffect.emit(GitHubListUiEffect.ShowSnackbar(message))
		}
	}
}
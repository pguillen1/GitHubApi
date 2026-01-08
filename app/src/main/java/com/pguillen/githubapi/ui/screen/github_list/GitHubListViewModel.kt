package com.pguillen.githubapi.ui.screen.github_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pguillen.githubapi.domain.model.toUi
import com.pguillen.githubapi.domain.usercase.getuserrepos.GetUserRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubListViewModel @Inject constructor(
    private val getUserReposUseCase: GetUserRepos
) : ViewModel() {

    private val _uiState = MutableStateFlow<GitHubListUiState>(GitHubListUiState.EmptyList)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<GitHubListUiEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val uiEffect = _uiEffect.asSharedFlow()

    private val _currentText = MutableStateFlow("")
    val currentText = _currentText.asStateFlow()

    fun onEvent(event: GitHubListUiEvent) {
        when (event) {

            is GitHubListUiEvent.OnTextChange -> {
                _currentText.value = event.text
            }

            GitHubListUiEvent.OnSearchClick -> {
                search()
            }

            is GitHubListUiEvent.OnRepoClick -> {
                navigateToDetail(owner = event.owner, repoName = event.repoName)
            }
        }
    }

    private fun search() {
        if (_currentText.value.isBlank()) {
            emitError("Introduce un texto")
            return
        }
        _uiState.value = GitHubListUiState.Loading
        viewModelScope.launch {
            try {
                val repos = getUserReposUseCase(_currentText.value).map {
                    it.toUi()
                }
                if (repos.isEmpty()) {
                    _uiState.value = GitHubListUiState.EmptyList
                } else {
                    _uiState.value = GitHubListUiState.Success(repos)
                }
            } catch (e: Exception) {
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
}
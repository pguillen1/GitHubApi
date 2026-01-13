package com.pguillen.githubapi.ui.screen.github_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pguillen.githubapi.domain.model.toUi
import com.pguillen.githubapi.domain.usercase.getrepodetail.GetRepoDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubDetailViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	val getRepoDetail: GetRepoDetail
) : ViewModel() {

	private val owner: String = checkNotNull(savedStateHandle["owner"])
	private val repoName: String = checkNotNull(savedStateHandle["repoName"])

	private val _uiState = MutableStateFlow<GitHubDetailState>(GitHubDetailState.Loading)
	val uiState: StateFlow<GitHubDetailState> = _uiState.asStateFlow()

	init {
		loadRepo()
	}

	fun onEvent(event: GitHubDetailEvent) {
		when (event) {
			GitHubDetailEvent.OnRetry -> loadRepo()
		}
	}

	private fun loadRepo() {
		_uiState.value = GitHubDetailState.Loading
		viewModelScope.launch {
			try {
				val repo =
					getRepoDetail.invoke(
						owner = owner,
						repoName = repoName
					).toUi()
				_uiState.value = GitHubDetailState.Success(repo)
			}
			catch (e: Exception) {
				_uiState.value = GitHubDetailState.Error("Ha habido un error al cargar el repo.")
			}
		}
	}
}
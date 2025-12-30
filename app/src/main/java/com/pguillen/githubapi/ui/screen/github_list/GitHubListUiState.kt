package com.pguillen.githubapi.ui.screen.github_list

import com.pguillen.githubapi.ui.model.RepoUi

sealed interface GitHubListUiState {

	object Loading : GitHubListUiState

	object EmptyList : GitHubListUiState

	data class Error(val error: String) : GitHubListUiState

	data class Success(val data: List<RepoUi>) : GitHubListUiState
}
package com.pguillen.githubapi.ui.screen.github_list

import com.pguillen.githubapi.ui.model.RepoUi

data class GitHubListUiState(
	val query: String = "",
	val isLoading: Boolean = false,
	val error: String? = null,
	val repos: List<RepoUi> = emptyList()
)
//sealed interface GitHubListUiState {
//
//	object Idle : GitHubListUiState
//
//	object Loading : GitHubListUiState
//
//	object EmptyList : GitHubListUiState
//
//	data class Error(val error: String) : GitHubListUiState
//
//	data class Success(val data: List<RepoUi>) : GitHubListUiState
//}
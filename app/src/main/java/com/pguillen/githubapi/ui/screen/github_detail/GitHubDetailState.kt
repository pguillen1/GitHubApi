package com.pguillen.githubapi.ui.screen.github_detail

import com.pguillen.githubapi.ui.model.RepoUi

sealed interface GitHubDetailState {
	data class Error(val message: String) : GitHubDetailState
	data object Loading : GitHubDetailState
	data class Success(val repo: RepoUi) : GitHubDetailState
}
package com.pguillen.githubapi.ui.screen.github_list

sealed interface GitHubListUiEvent {

	data class OnTextChange(
		val text: String
	) : GitHubListUiEvent

	data object OnSearchClick : GitHubListUiEvent

    data class OnRepoClick(
        val owner: String,
        val repoName: String
    ) : GitHubListUiEvent

	data object OnRetry : GitHubListUiEvent
}
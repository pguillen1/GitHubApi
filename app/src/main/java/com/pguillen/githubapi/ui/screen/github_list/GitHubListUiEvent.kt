package com.pguillen.githubapi.ui.screen.github_list

sealed interface GitHubListUiEvent {

	data class OnTextChange(
		val text: String
	) : GitHubListUiEvent

	object OnSearchClick : GitHubListUiEvent
}
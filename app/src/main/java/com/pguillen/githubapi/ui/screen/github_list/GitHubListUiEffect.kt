package com.pguillen.githubapi.ui.screen.github_list

sealed interface GitHubListUiEffect {

	data class ShowSnackbar(
		val message: String?
	) : GitHubListUiEffect

    data class NavigateToRepoDetail(
        val owner: String,
        val repoName: String
    ) : GitHubListUiEffect
}
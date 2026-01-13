package com.pguillen.githubapi.ui.screen.github_detail

sealed interface GitHubDetailEvent {
	data object OnRetry : GitHubDetailEvent
}
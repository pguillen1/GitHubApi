package com.pguillen.githubapi.ui.screen.github_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pguillen.githubapi.ui.model.RepoUi
import com.pguillen.githubapi.ui.screen.github_list.ErrorScreen
import com.pguillen.githubapi.ui.screen.github_list.LoadingScreen

@Composable
fun GitHubDetailScreen(
	viewModel: GitHubDetailViewModel = hiltViewModel()
) {

	val state by viewModel.uiState.collectAsState()

	Scaffold { paddingValues ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues),
		) {
			when (val s = state) {
				GitHubDetailState.Loading -> LoadingScreen()
				is GitHubDetailState.Error -> ErrorScreen(s.message)
				is GitHubDetailState.Success -> DetailScreen(s.repo)
			}
		}
	}
}

@Composable
fun DetailScreen(repo: RepoUi) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Name: ${repo.name}")
		Text("Description: ${repo.description ?: "Sin descripción"}")
		Text("Stars: ${repo.stars}")
	}
}
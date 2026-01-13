package com.pguillen.githubapi.ui.screen.github_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.pguillen.githubapi.ui.model.RepoUi

@Composable
fun GitHubListScreen(
	viewModel: GitHubListViewModel = hiltViewModel(),
	onNavigateToDetail: (String, String) -> Unit
) {

	val state by viewModel.uiState.collectAsState()
	val snackbarHostState = remember { SnackbarHostState() }

	LaunchedEffect(Unit) {
		viewModel.uiEffect.collect { effect ->
			when (effect) {
				is GitHubListUiEffect.ShowSnackbar -> {
					snackbarHostState.showSnackbar(
						message = effect.message!!
					)
				}

				is GitHubListUiEffect.NavigateToRepoDetail -> {
					onNavigateToDetail(effect.owner, effect.repoName)
				}
			}
		}
	}

	Scaffold(
		modifier = Modifier.imePadding(),
		snackbarHost = { SnackbarHost(snackbarHostState) }
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			SearchInput(
				text = state.query,
				onTextChange = { viewModel.onEvent(GitHubListUiEvent.OnTextChange(it)) },
				onSearchClick = { viewModel.onEvent(GitHubListUiEvent.OnSearchClick) },
				onRetryClick = { viewModel.onEvent(GitHubListUiEvent.OnRetry) }
			)

			if (state.isLoading && state.repos.isNotEmpty()) {
				LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
			}

			when {
				state.repos.isNotEmpty() -> {
					SuccessScreen(
						repos = state.repos,
						onRepoClick = { owner, repoName ->
							viewModel.onEvent(
								GitHubListUiEvent.OnRepoClick(
									owner,
									repoName
								)
							)
						}
					)
				}

				state.isLoading -> {
					LoadingScreen()
				}

				else -> {
					EmptyListScreen()
				}
			}
		}
	}
}

@Composable
fun SearchInput(
	text: String,
	onTextChange: (String) -> Unit,
	onSearchClick: () -> Unit,
	onRetryClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth(),
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	) {
		OutlinedTextField(
			modifier = Modifier
				.padding(8.dp)
				.weight(1f),
			value = text,
			onValueChange = { onTextChange(it) },
			singleLine = true,
			placeholder = { Text("Introduce un usuario") }
		)
		OutlinedIconButton(
			modifier = Modifier.padding(end = 8.dp),
			shape = RoundedCornerShape(4.dp),
			onClick = { onSearchClick() }
		) {
			Icon(imageVector = Icons.Default.Search, contentDescription = "Search button")
		}

		OutlinedIconButton(
			modifier = Modifier.padding(end = 8.dp),
			shape = RoundedCornerShape(4.dp),
			onClick = { onRetryClick() }
		) {
			Icon(imageVector = Icons.Default.Refresh, contentDescription = "Retry button")
		}
	}
}

@Composable
fun EmptyListScreen() {
	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Text("No hay repositorios.")
	}
}

@Composable
fun SuccessScreen(
	repos: List<RepoUi>,
	onRepoClick: (String, String) -> Unit
) {
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
	) {
		items(repos) { repo ->
			RepoItem(
				repo = repo,
				onRepoClick = { owner, repoName ->
					onRepoClick(owner, repoName)
				}
			)
		}
	}
}

@Composable
fun LoadingScreen() {
	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator()
	}
}

@Composable
fun ErrorScreen(message: String) {
	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Text(message)
	}
}

@Composable
fun RepoItem(
	repo: RepoUi,
	onRepoClick: (String, String) -> Unit
) {
	ListItem(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onRepoClick(repo.owner, repo.name) },
		headlineContent = { Text(repo.name) }
	)
}
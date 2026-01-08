package com.pguillen.githubapi.ui.screen.github_list

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
	viewModel: GitHubListViewModel = hiltViewModel()
) {

	val state by viewModel.uiState.collectAsState()
	val snackbarHostState = remember { SnackbarHostState() }
	val text by viewModel.currentText.collectAsState()

	LaunchedEffect(Unit) {
		viewModel.uiEffect.collect { effect ->
			when (effect) {
				is GitHubListUiEffect.ShowSnackbar -> {
					snackbarHostState.showSnackbar(
						message = effect.message!!
					)
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
				text = text,
				onTextChange = { viewModel.onEvent(GitHubListUiEvent.OnTextChange(it)) },
				onSearchClick = { viewModel.onEvent(GitHubListUiEvent.OnSearchClick) }
			)

			when (val s = state) {
				GitHubListUiState.EmptyList -> EmptyListScreen()
				GitHubListUiState.Loading -> LoadingScreen()
				is GitHubListUiState.Error -> ErrorScreen(s.error)
				is GitHubListUiState.Success -> SuccessScreen(s.data)
			}
		}
	}
}

@Composable
fun SearchInput(
	text: String,
	onTextChange: (String) -> Unit,
	onSearchClick: () -> Unit
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
			onValueChange = { onTextChange(it) }
		)
		OutlinedIconButton(
			modifier = Modifier.padding(end = 8.dp),
			shape = RoundedCornerShape(4.dp),
			onClick = { onSearchClick() }
		) {
			Icon(imageVector = Icons.Default.Search, contentDescription = "Search button")
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
fun SuccessScreen(repos: List<RepoUi>) {
	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
	) {
		items(repos) { repo ->
			RepoItem(repo)
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
fun RepoItem(repo: RepoUi) {
	ListItem(
		modifier = Modifier
			.fillMaxWidth(),
		headlineContent = { Text(repo.name) }
	)
}
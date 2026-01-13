package com.pguillen.githubapi.ui.navigation

sealed class Routes(val route: String) {
	data object RepoList : Routes(route = "repo_list")
	data object RepoDetail : Routes(route = "repo_detail/{owner}/{repoName}") {
		fun createRoute(
			owner: String,
			repoName: String
		): String = "repo_detail/$owner/$repoName"
	}
}
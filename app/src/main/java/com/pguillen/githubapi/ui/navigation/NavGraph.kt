package com.pguillen.githubapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pguillen.githubapi.ui.screen.github_detail.GitHubDetailScreen
import com.pguillen.githubapi.ui.screen.github_list.GitHubListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
	NavHost(
		navController = navController,
		startDestination = Routes.RepoList.route
	) {
		composable(route = Routes.RepoList.route) {
			GitHubListScreen(
				onNavigateToDetail = { owner, repoName ->
					navController.navigate(
						Routes.RepoDetail.createRoute(owner, repoName)
					)
				}
			)
		}

		composable(
			route = Routes.RepoDetail.route,
			arguments = listOf(
				navArgument("owner") { type = NavType.StringType },
				navArgument("repoName") { type = NavType.StringType }
			)
		) {
			GitHubDetailScreen()
		}
	}
}
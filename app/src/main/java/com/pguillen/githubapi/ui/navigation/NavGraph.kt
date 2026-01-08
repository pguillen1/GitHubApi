package com.pguillen.githubapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pguillen.githubapi.ui.screen.github_list.GitHubListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.RepoList
    ) {
        composable<Routes.RepoList> {
            GitHubListScreen()
        }

        composable<Routes.RepoDetail> {

        }
    }
}
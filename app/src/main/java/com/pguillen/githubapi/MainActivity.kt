package com.pguillen.githubapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.pguillen.githubapi.ui.navigation.AppNavGraph
import com.pguillen.githubapi.ui.theme.GitHubApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			GitHubApiTheme {
				val navController = rememberNavController()
				AppNavGraph(navController)
			}
		}
	}
}
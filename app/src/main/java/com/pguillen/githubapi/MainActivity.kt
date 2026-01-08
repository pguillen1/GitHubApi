package com.pguillen.githubapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pguillen.githubapi.ui.screen.github_list.GitHubListScreen
import com.pguillen.githubapi.ui.theme.GitHubApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			GitHubApiTheme {
				GitHubListScreen()
			}
		}
	}
}
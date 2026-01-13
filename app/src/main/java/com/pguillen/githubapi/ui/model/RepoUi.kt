package com.pguillen.githubapi.ui.model

data class RepoUi(
	val id: Long,
	val name: String,
	val description: String?,
	val stars: Int,
	val owner: String
)


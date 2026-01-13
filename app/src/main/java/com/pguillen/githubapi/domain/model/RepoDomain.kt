package com.pguillen.githubapi.domain.model

import com.pguillen.githubapi.ui.model.RepoUi

data class RepoDomain(
	val id: Long,
	val name: String,
	val fullName: String,
	val description: String?,
	val stars: Int,
	val language: String?,
	val ownerName: String,
	val ownerAvatarUrl: String
)

fun RepoDomain.toUi() = RepoUi(
	id = id,
	name = name,
	description = description,
	stars = stars,
	owner = ownerName
)

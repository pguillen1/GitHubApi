package com.pguillen.githubapi

import com.pguillen.githubapi.domain.model.RepoDomain

fun createRepoDomain(id: Int): RepoDomain =
	RepoDomain(
		id = id.toLong(),
		name = "Test name $id",
		fullName = "Test full name $id",
		description = "Test description $id",
		stars = id,
		language = "Español",
		ownerName = "Test owner name $id",
		ownerAvatarUrl = "Test avatar url $id"
	)
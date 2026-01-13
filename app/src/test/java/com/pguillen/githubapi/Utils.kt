package com.pguillen.githubapi

import com.pguillen.githubapi.domain.model.RepoDomain

fun createRepoDomainById(id: Int): RepoDomain =
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

fun createRepoDomainByOwnerName(owner: String, repoName: String): RepoDomain =
	RepoDomain(
		id = 1,
		name = repoName,
		fullName = "Test full name",
		description = "Test description",
		stars = 1,
		language = "Español",
		ownerName = owner,
		ownerAvatarUrl = "Test avatar url"
	)
package com.pguillen.githubapi.data.remote.dto

import com.pguillen.githubapi.domain.model.RepoDomain
import com.squareup.moshi.Json

data class RepoDto(
	val id: Long,
	val name: String,
	@Json(name = "full_name")
	val fullName: String,
	val description: String?,
	@Json(name = "stargazers_count")
	val stargazersCount: Int,
	val language: String?,
	val owner: OwnerDto
)

fun RepoDto.toDomain(): RepoDomain {
	return RepoDomain(
		id = id,
		name = name,
		fullName = fullName,
		description = description,
		stars = stargazersCount,
		language = language,
		ownerName = owner.login,
		ownerAvatarUrl = owner.avatar_url
	)
}
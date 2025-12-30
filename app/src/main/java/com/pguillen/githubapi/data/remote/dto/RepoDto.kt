package com.pguillen.githubapi.data.remote.dto

import com.pguillen.githubapi.domain.model.RepoDomain

data class RepoDto(
	val id: Long,
	val name: String,
	val full_name: String,
	val description: String?,
	val stargazers_count: Int,
	val language: String?,
	val owner: OwnerDto
)

fun RepoDto.toDomain(): RepoDomain {
	return RepoDomain(
		id = id,
		name = name,
		fullName = full_name,
		description = description,
		stars = stargazers_count,
		language = language,
		ownerName = owner.login,
		ownerAvatarUrl = owner.avatar_url
	)
}
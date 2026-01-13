package com.pguillen.githubapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pguillen.githubapi.data.remote.dto.RepoDto
import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.ui.model.RepoUi

@Entity(tableName = "repo_table")
data class RepoEntity(
	@PrimaryKey	val id: Long,
	val name: String,
	val fullName: String,
	val description: String?,
	val stars: Int,
	val language: String?,
	val ownerName: String,
	val ownerAvatarUrl: String
)

fun RepoEntity.toDomain(): RepoDomain {
	return RepoDomain(
		id = id,
		name = name,
		fullName = fullName,
		description = description,
		stars = stars,
		language = language,
		ownerName = ownerName,
		ownerAvatarUrl = ownerAvatarUrl
	)
}

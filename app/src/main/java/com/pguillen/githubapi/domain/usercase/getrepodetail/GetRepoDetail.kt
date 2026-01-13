package com.pguillen.githubapi.domain.usercase.getrepodetail

import com.pguillen.githubapi.domain.model.RepoDomain

interface GetRepoDetail {
	suspend operator fun invoke(
		owner: String,
		repoName: String
	): RepoDomain
}
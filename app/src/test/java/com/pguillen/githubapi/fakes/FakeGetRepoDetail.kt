package com.pguillen.githubapi.fakes

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.usercase.getrepodetail.GetRepoDetail

class FakeGetRepoDetail: GetRepoDetail {

	var shouldThrowError = false
	var repoDetail: RepoDomain? = null
	override suspend fun invoke(
		owner: String,
		repoName: String
	): RepoDomain {
		if (shouldThrowError) {
			throw RuntimeException("Error")
		}
		return repoDetail!!
	}
}
package com.pguillen.githubapi.fakes

import com.pguillen.githubapi.domain.model.RepoDomain
import com.pguillen.githubapi.domain.usercase.GetUserReposUseCase

class FakeGetUserReposUseCase: GetUserReposUseCase {

	var repos = emptyList<RepoDomain>()
}
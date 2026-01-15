package com.pguillen.githubapi.data.remote.api

import com.pguillen.githubapi.data.remote.dto.RepoDto
import com.pguillen.githubapi.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

	@GET("search/repositories")
	suspend fun searchRepositories(
		@Query("q") query: String,
		@Query("sort") sort: String = "stars",
		@Query("order") order: String = "desc"
	): SearchResponseDto

	@GET("users/{username}/repos")
	suspend fun getUserRepos(
		@Path("username") username: String
	): List<RepoDto>

	@GET(value = "repos/{owner}/{repo}")
	suspend fun getRepoDetail(
		@Path("owner") owner: String,
		@Path("repo") repoName: String
	): RepoDto
}
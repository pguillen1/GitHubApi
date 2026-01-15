package com.pguillen.githubapi.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pguillen.githubapi.data.local.entity.GitHubDatabase
import com.pguillen.githubapi.data.local.entity.RepoDao
import com.pguillen.githubapi.data.local.entity.RepoEntity
import com.pguillen.githubapi.data.local.entity.toDomain
import com.pguillen.githubapi.data.remote.api.GitHubApi
import com.pguillen.githubapi.data.remote.repository.GitHubRepositoryImpl
import com.pguillen.githubapi.domain.repository.GitHubRepository
import com.pguillen.githubapi.readJsonFile
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.Headers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertFailsWith

@RunWith(AndroidJUnit4::class)
class GitHubRepositoryImplementationTest {

	private lateinit var server: MockWebServer
	private lateinit var db: GitHubDatabase
	private lateinit var repoDao: RepoDao
	private lateinit var retrofit: Retrofit
	private lateinit var api: GitHubApi
	private lateinit var repository: GitHubRepository

	@Before
	fun setup() {
		server = MockWebServer()
		server.start()
		db = Room.inMemoryDatabaseBuilder(
			context = ApplicationProvider.getApplicationContext(),
			klass = GitHubDatabase::class.java
		)
			.allowMainThreadQueries()
			.build()
		repoDao = db.getRepoDao()
		retrofit = Retrofit.Builder()
			.baseUrl(server.url("/"))
			.addConverterFactory(
				MoshiConverterFactory.create(
					Moshi.Builder()
						.add(KotlinJsonAdapterFactory())
						.build()
				)
			)
			.build()
		api = retrofit.create(GitHubApi::class.java)
		repository = GitHubRepositoryImpl(api, repoDao)
	}

	@After
	fun tearDown() {
		db.close()
		server.close()
	}

	@Test
	fun refreshUserRepos_saves_data_in_room_and_observeUserRepos_emits() = runTest {
		val username = "google"
		server.enqueue(
			MockResponse(
				code = 200,
				headers = Headers.headersOf("Content-Type", "application/json"),
				body = readJsonFile("user_repos.json")
			)
		)
		repository.refreshUserRepos(username)
		val flow = repository.observeUserRepos(username).first()

		assertEquals(2, flow.size)
		assertEquals(1, flow.first().id)
		assertEquals("Test name 1", flow.first().name)
		assertEquals("Test full name 1", flow.first().fullName)
		assertEquals(1, flow.first().stars)
		assertEquals("google", flow.first().ownerName)
	}

	@Test
	fun refreshUserRepos_calls_correct_method_and_path() = runTest {
		val username = "google"
		server.enqueue(
			MockResponse(
				code = 200,
				headers = Headers.headersOf("Content-Type", "application/json"),
				body = readJsonFile("user_repos.json")
			)
		)
		repository.refreshUserRepos(username)
		val request = server.takeRequest()
		assertEquals("GET", request.method)
		assertEquals("/users/${username}/repos", request.url.encodedPath)
	}

	@Test
	fun refreshUserRepos_replace_data_not_accumulate() = runTest {
		val username = "google"
		server.enqueue(
			MockResponse(
				code = 200,
				headers = Headers.headersOf("Content-Type", "application/json"),
				body = readJsonFile("user_repos.json")
			)
		)
		repository.refreshUserRepos(username)
		var flow = repository.observeUserRepos(username).first()
		assertEquals(2, flow.size)
		server.enqueue(
			MockResponse(
				code = 200,
				headers = Headers.headersOf("Content-Type", "application/json"),
				body = readJsonFile("user_repo.json")
			)
		)
		repository.refreshUserRepos(username)
		flow = repository.observeUserRepos(username).first()
		assertEquals(1, flow.size)
	}

	@Test
	fun cache_not_deleted_when_connection_fails() = runTest {
		val username = "google"
		val repos = listOf(
			RepoEntity(
				id = 1,
				name = "Test name",
				fullName = "Test full name",
				description = null,
				stars = 1,
				language = null,
				ownerName = username,
				ownerAvatarUrl = "url"
			)
		)
		repoDao.insertRepos(repos)
		server.enqueue(
			MockResponse(
				code = 500,
				headers = Headers.headersOf("Content-Type", "application/json"),
				body = readJsonFile("user_repo.json")
			)
		)
		assertFailsWith<HttpException> {
			repository.refreshUserRepos(username)
		}
		val flow = repository.observeUserRepos(username).first()
		assertEquals(repos.map { it.toDomain() }, flow)
	}
}
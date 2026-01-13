package com.pguillen.githubapi.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

	@Query("SELECT * FROM repo_table WHERE ownerName = :username")
	fun observeRepoByOwner(username: String): Flow<List<RepoEntity>>

	@Insert(onConflict = REPLACE)
	suspend fun insertRepos(repos: List<RepoEntity>)

	@Query("DELETE FROM repo_table WHERE ownerName = :username")
	suspend fun deleteReposByOwner(username: String)

	@Transaction
	suspend fun replaceReposForUser(username: String, repos: List<RepoEntity>) {
		deleteReposByOwner(username)
		insertRepos(repos)
	}
}
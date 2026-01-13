package com.pguillen.githubapi.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepoEntity::class], version = 1)
abstract class GitHubDatabase : RoomDatabase() {
	abstract fun getRepoDao(): RepoDao
}
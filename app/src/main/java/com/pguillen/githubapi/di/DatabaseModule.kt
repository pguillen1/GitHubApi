package com.pguillen.githubapi.di

import android.content.Context
import androidx.room.Room
import com.pguillen.githubapi.data.local.entity.GitHubDatabase
import com.pguillen.githubapi.data.local.entity.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context): GitHubDatabase {
		return Room.databaseBuilder(
			context = context,
			klass = GitHubDatabase::class.java,
			name = "github.db"
		).build()
	}

	@Provides
	@Singleton
	fun provideRepoDao(database: GitHubDatabase): RepoDao = database.getRepoDao()

}
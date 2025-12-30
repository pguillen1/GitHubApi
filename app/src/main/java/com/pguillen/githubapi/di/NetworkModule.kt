package com.pguillen.githubapi.di

import com.pguillen.githubapi.data.remote.api.GitHubApi
import com.pguillen.githubapi.data.remote.repository.GitHubRepositoryImpl
import com.pguillen.githubapi.domain.repository.GitHubRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideMoshi(): Moshi = Moshi.Builder().build()

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient =
		OkHttpClient.Builder()
			.addNetworkInterceptor(
				HttpLoggingInterceptor().apply {
					level = HttpLoggingInterceptor.Level.BODY
				}
			)
			.build()

	@Provides
	@Singleton
	fun provideRetrofit(
		moshi: Moshi,
		okHttpClient: OkHttpClient
	): Retrofit =
		Retrofit.Builder()
			.baseUrl("https://api.github.com/")
			.client(okHttpClient)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.build()

	@Provides
	@Singleton
	fun provideGitHubApi(retrofit: Retrofit): GitHubApi =
		retrofit.create(GitHubApi::class.java)

	@Provides
	@Singleton
	fun provideRepository(api: GitHubApi): GitHubRepository {
		return GitHubRepositoryImpl(api)
	}

}
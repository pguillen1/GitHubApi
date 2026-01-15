package com.pguillen.githubapi

import androidx.test.platform.app.InstrumentationRegistry

fun readJsonFile(fileName: String): String {
	val context = InstrumentationRegistry.getInstrumentation().context
	val body = context.assets.open(fileName)
		.bufferedReader()
		.use { it.readText() }
	return body
}
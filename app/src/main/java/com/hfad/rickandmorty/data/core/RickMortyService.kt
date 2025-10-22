package com.hfad.rickandmorty.data.core

import android.R.attr.type
import android.media.Image
import android.util.Log
import com.hfad.rickandmorty.data.model.Location
import com.hfad.rickandmorty.data.model.Origin
import com.hfad.rickandmorty.data.model.Response
import com.hfad.rickandmorty.data.model.Results
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.builtins.serializer

class RickMortyService {
    private val client = RickMortyClientCore.instance.client
    private val apiUrl = "https://rickandmortyapi.com/api"

    suspend fun getHeroes(
        page: Int = 1,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): Response {
        val response = client.get("$apiUrl/character") {
            url {
                parameters.append("page", page.toString())
                name?.let { parameters.append("name", it) }
                status?.let { parameters.append("status", it) }
                species?.let { parameters.append("species", it) }
                gender?.let { parameters.append("gender", it) }
            }
        }
        Log.d("Service", "URL: ${response.request.url}")
        Log.d("Service", "Status: ${response.status}")
        Log.d("Service", "Response body: ${response.bodyAsText()}")

        return RickMortyClientCore.instance.serializer.decodeFromString<Response>(response.bodyAsText())

    }

    suspend fun getHero(
        id: Int
    ): Results {
        return client.get("$apiUrl/character/$id").body()
    }

    suspend fun getAllHero(): List<Results> {
        val allHeroes = mutableListOf<Results>()
        var page = 1
        var totalPages = 1

        try {
            while (page <= totalPages) {
                val response = getHeroes(page = page)
                allHeroes.addAll(response.results)
                totalPages = response.info.pages
                page++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return allHeroes
    }
}
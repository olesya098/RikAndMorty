package com.hfad.rickandmorty.data.core

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RickMortyClientCore {
    companion object{
        val instance: RickMortyClientCore = RickMortyClientCore()
    }
    val serializer = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(serializer)
        }
        install(io.ktor.client.plugins.HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }
    }
}
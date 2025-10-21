package com.hfad.rickandmorty.domain.repository

import android.media.Image
import com.hfad.rickandmorty.data.core.RickMortyService
import com.hfad.rickandmorty.data.model.Response
import com.hfad.rickandmorty.data.model.Results

class HeroRepository {
    private val service = RickMortyService()

    suspend fun getHeroes(
        page: Int = 1,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): Response {
        return service.getHeroes(page, name, status,species, gender)
    }

    suspend fun getHero(
        id: Int
    ): Results {
        return service.getHero(id)
    }

    suspend fun getAllHero(): List<Results> {
        return service.getAllHero()
    }
}
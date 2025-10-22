package com.hfad.rickandmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String> = listOf(),
    val url: String,
    val created: String
)
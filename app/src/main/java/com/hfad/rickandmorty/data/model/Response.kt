package com.hfad.rickandmorty.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val info: Info,
    val results: List<Results>
)
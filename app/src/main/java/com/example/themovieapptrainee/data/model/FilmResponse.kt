package com.example.themovieapptrainee.data.model

import com.squareup.moshi.Json

data class FilmResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "year") val year: String,
    @Json(name = "rating") val rating: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "description") val description: String,
)

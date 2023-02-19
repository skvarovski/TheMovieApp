package com.example.themovieapptrainee.data.model

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "rating") val rating: String,
    @Json(name = "year") val year: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "description") val description: String,
    @Json(name = "for_age") val forAge: String
)

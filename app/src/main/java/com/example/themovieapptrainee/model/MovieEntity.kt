package com.example.themovieapptrainee.model

import android.net.Uri

data class MovieEntity(
    val id: Int,
    val title: String,
    val year: String,
    val rating: String,
    val imageUrl: Uri,
    val description: String
)

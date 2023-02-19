package com.example.themovieapptrainee.data

import com.example.themovieapptrainee.data.model.FilmResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface FilmAPI {

    @GET("/v1/api/films.json")
    @Headers("mocks:true")
    suspend fun getFilms(): Response<List<FilmResponse>>
}

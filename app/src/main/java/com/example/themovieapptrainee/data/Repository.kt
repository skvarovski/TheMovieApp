package com.example.themovieapptrainee.data

import android.content.Context
import android.net.Uri
import com.example.themovieapptrainee.model.MovieEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Repository(private val context: Context) {

    private val host = "https://amk-tools.ru"
    private fun retrofitBuilder(): FilmAPI {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(MockRequestInterceptor(context))
            .addInterceptor(logging)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://ya.ru/")
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(FilmAPI::class.java)
    }

    suspend fun getFilms(): Flow<DataState<List<MovieEntity>>> = flow {
        emit(DataState.loading())
        try {
            val filmResponse = retrofitBuilder().getFilms()
            if (filmResponse.isSuccessful && filmResponse.body() != null) {
                val movieList = mutableListOf<MovieEntity>()
                filmResponse.body()?.forEach { item ->
                    movieList.add(
                        MovieEntity(
                            id = item.id,
                            title = item.title,
                            year = item.year,
                            rating = item.rating,
                            imageUrl = Uri.parse("$host/images/sw/${item.imageUrl}"),
                            description = item.description,
                        ),
                    )
                }
                if (movieList.isEmpty()) {
                    emit(DataState.error("Empty list"))
                } else {
                    emit(DataState.success(movieList.toList()))
                }
            }
        } catch (e: java.lang.Exception) {
            emit(DataState.error("try-catch error ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}

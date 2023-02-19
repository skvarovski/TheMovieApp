package com.example.themovieapptrainee.data

import android.content.Context
import android.net.Uri
import com.example.themovieapptrainee.model.TheMovieEntity
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

    suspend fun getMovies(): Flow<DataState<List<TheMovieEntity>>> = flow {
        emit(DataState.loading())
        try {
            val apiResponse = retrofitBuilder().getMovies()
            if (apiResponse.isSuccessful && apiResponse.body() != null) {
                val movieList = mutableListOf<TheMovieEntity>()
                apiResponse.body()?.forEach { item ->
                    movieList.add(
                        TheMovieEntity(
                            id = item.id,
                            title = item.title,
                            rating = item.rating,
                            imageUrl = "$host/images/themovieapp/${item.imageUrl}",
                            description = item.description,
                            forAge = item.forAge,
                        ),
                    )
                }
                if (movieList.isNotEmpty()) {
                    emit(DataState.success(movieList.toList()))
                } else {
                    emit(DataState.error("List empty"))
                }
            }
        } catch (e: java.lang.Exception) {
            emit(DataState.error(e.message ?: "error try-catch"))
        }
    }.flowOn(Dispatchers.IO)
}

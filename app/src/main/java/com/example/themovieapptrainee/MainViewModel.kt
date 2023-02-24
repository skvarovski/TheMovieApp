package com.example.themovieapptrainee

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovieapptrainee.data.DataState
import com.example.themovieapptrainee.data.Repository
import com.example.themovieapptrainee.model.TheMovieEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(var context: Application) : AndroidViewModel(context) {

    private val _movieItems = MutableStateFlow<List<TheMovieEntity>>(emptyList())
    val movieItems: StateFlow<List<TheMovieEntity>> = _movieItems.asStateFlow()

    fun getMovieFromRepository() {
        viewModelScope.launch {
            val repository = Repository(context)
            repository.getMovies().collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _movieItems.emit(dataState.data)
                        Log.d("TEST", "data is = ${dataState.data}")
                    }
                    is DataState.Error -> {
                        _movieItems.emit(emptyList())
                        Log.d("TEST", "Error ${dataState.message}")
                    }
                    is DataState.Loading -> {
                        _movieItems.emit(emptyList())
                        Log.d("TEST", "Loading...")
                    }
                }
            }
        }
    }
}

package ru.geekbrains.android2.movieapp.viewmodel

import ru.geekbrains.android2.movieapp.model.Movie

sealed class AppState {
    data class Success(val movieData: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

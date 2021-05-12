package ru.geekbrains.android2.movieapp.viewmodel

import ru.geekbrains.android2.movieapp.model.Movie

sealed class AppStateDetails{
    data class Success(val movie: Movie) : AppStateDetails()
    data class Error(val error: Throwable) : AppStateDetails()
    object Loading : AppStateDetails()
}


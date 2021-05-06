package ru.geekbrains.android2.movieapp.viewmodel

import ru.geekbrains.android2.movieapp.model.Category

sealed class AppState {
    data class Success(val categoryData: List<Category>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

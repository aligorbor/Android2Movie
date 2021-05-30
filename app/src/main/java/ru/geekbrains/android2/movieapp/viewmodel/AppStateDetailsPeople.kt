package ru.geekbrains.android2.movieapp.viewmodel

import ru.geekbrains.android2.movieapp.model.Person

sealed class AppStateDetailsPeople {
    data class Success(val people: Person) : AppStateDetailsPeople()
    data class Error(val error: Throwable) : AppStateDetailsPeople()
    object Loading : AppStateDetailsPeople()
}


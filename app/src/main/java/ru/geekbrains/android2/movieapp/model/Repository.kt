package ru.geekbrains.android2.movieapp.model

import ru.geekbrains.android2.movieapp.interactors.StringsInteractor

interface Repository {
    fun getCategoriesFromRemoteStorage(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean
    ): List<Category>

    fun getMovieDetailFromRemoteStorage(movie: Movie): Movie

    fun getPersonsPopularFromRemoteStorage(
        isRus: Boolean,
        adult: Boolean
    ): Persons

    fun getPersonDetailFromRemoteStorage(person: Person): Person

    fun getAllHistory(): List<Movie>
    fun saveToHistory(movie: Movie)
    fun getAllFavorite(): List<Movie>
    fun saveToFavorite(movie: Movie)
    fun deleteFromFavorite(id: Int)
    fun isFavorite(id: Int): Boolean
    fun getNote(id: Int): String

}
package ru.geekbrains.android2.movieapp.model

import ru.geekbrains.android2.movieapp.interactors.StringsInteractor

interface Repository {
    fun getCategoriesFromRemoteStorage(
        isRus: Boolean,
        interactor: StringsInteractor
    ): List<Category>

    fun getMovieDetailFromRemoteStorage(movie: Movie): Movie
}
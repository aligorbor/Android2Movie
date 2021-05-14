package ru.geekbrains.android2.movieapp.model

import ru.geekbrains.android2.movieapp.interactors.StringsInteractor

class RepositoryImpl : Repository {

    override fun getCategoriesFromRemoteStorage(isRus: Boolean, interactor: StringsInteractor) =
        MoviesLoader.loadCategories(isRus, interactor)

    override fun getMovieDetailFromRemoteStorage(movie: Movie) = MoviesLoader.loadMovieDetail(movie)

}
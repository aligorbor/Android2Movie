package ru.geekbrains.android2.movieapp.model

class RepositoryImpl : Repository {

    override fun getCategoriesFromRemoteStorage(isRus: Boolean) = MoviesLoader.loadCategories(isRus)
    override fun getMovieDetailFromRemoteStorage(movie: Movie) = MoviesLoader.loadMovieDetail(movie)

}
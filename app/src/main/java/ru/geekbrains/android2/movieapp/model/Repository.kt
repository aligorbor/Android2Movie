package ru.geekbrains.android2.movieapp.model

interface Repository {
    fun getCategoriesFromRemoteStorage(isRus: Boolean): List<Category>
    fun getMovieDetailFromRemoteStorage(movie: Movie): Movie
}
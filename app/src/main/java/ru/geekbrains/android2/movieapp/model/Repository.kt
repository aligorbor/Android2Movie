package ru.geekbrains.android2.movieapp.model

interface Repository {
    fun getMoviesNowPlayingFromLocalStorageRus(): List<Movie>
    fun getMoviesNowPlayingFromLocalStorageWorld(): List<Movie>
}
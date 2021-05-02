package ru.geekbrains.android2.movieapp.model

class RepositoryImpl:Repository  {

    override fun getMoviesNowPlayingFromLocalStorageRus(): List<Movie> {
        return getRussianMoviesNowPlaying()
    }

    override fun getMoviesNowPlayingFromLocalStorageWorld(): List<Movie> {
        return getWorldMoviesNowPlaying()
    }
}
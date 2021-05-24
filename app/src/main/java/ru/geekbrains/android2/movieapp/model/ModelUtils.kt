package ru.geekbrains.android2.movieapp.model

import ru.geekbrains.android2.movieapp.model.rest.ImageNotFound
import ru.geekbrains.android2.movieapp.model.rest.endPointImage
import ru.geekbrains.android2.movieapp.model.rest.imageLink
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.CategoryDTO
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.GenreDTO
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.GenresDTO
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.MovieDetailDTO

fun toMovies(categoryDTO: CategoryDTO?): MutableList<Movie> {
    val movies: MutableList<Movie> = mutableListOf()
    categoryDTO?.let {
        for (result in categoryDTO.results) {
            movies.add(
                Movie(
                    adult = result?.adult ?: false,
                    backdrop_path = result?.backdrop_path ?: "",
                    id = result?.id ?: 0,
                    original_language = result?.original_language ?: "",
                    original_title = result?.original_title ?: "",
                    overview = result?.overview ?: "",
                    popularity = result?.popularity ?: 0.0,
                    poster_path = "$imageLink$endPointImage${result?.poster_path ?: ImageNotFound}",
                    release_date = result?.release_date ?: "",
                    title = result?.title ?: "",
                    video = result?.video ?: false,
                    vote_average = result?.vote_average ?: 0.0,
                    vote_count = result?.vote_count ?: 0
                )
            )
        }
    }
    return movies
}

fun toMovieDetail(movie: Movie, movieDetailDTO: MovieDetailDTO?): Movie {
    movie.original_title = movieDetailDTO?.original_title ?: ""
    movie.overview = movieDetailDTO?.overview ?: ""
    movie.vote_count = movieDetailDTO?.vote_count ?: 0

    movie.budget = movieDetailDTO?.budget ?: 0
    movie.revenue = movieDetailDTO?.revenue ?: 0
    movie.runtime = movieDetailDTO?.runtime ?: 0
    movieDetailDTO?.let {
        movie.genres = ""
        for (genre in movieDetailDTO.genres) {
            movie.genres = "${movie.genres}${genre?.name} "
        }
    }
    return movie
}

fun toGenres(genresDTO: GenresDTO?): MutableList<Genre> {
    val listOfGenres = mutableListOf<Genre>()
    genresDTO?.let {
        for (genre in genresDTO.genres) {
            listOfGenres.add(
                Genre(
                    id = genre?.id ?: 0,
                    name = genre?.name ?: ""
                )
            )
        }
    }
    return listOfGenres
}
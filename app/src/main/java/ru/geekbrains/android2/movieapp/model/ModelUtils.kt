package ru.geekbrains.android2.movieapp.model

import ru.geekbrains.android2.movieapp.model.rest.ImageNotFound
import ru.geekbrains.android2.movieapp.model.rest.endPointImage
import ru.geekbrains.android2.movieapp.model.rest.imageLink
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.*

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

fun toPersons(personsDTO: PersonsDTO?): MutableList<Person> {
    val persons: MutableList<Person> = mutableListOf()
    personsDTO?.let {
        for (result in personsDTO.results) {
            persons.add(
                Person(
                    adult = result?.adult ?: false,
                    gender = result?.gender ?: 0,
                    id = result?.id ?: 0,
                    known_for = toMovies(CategoryDTO(1, result?.known_for ?: arrayOf(), 1, 0)),
                    known_for_department = result?.known_for_department ?: "",
                    name = result?.name ?: "",
                    popularity = result?.popularity ?: 0.0,
                    profile_path = "$imageLink$endPointImage${result?.profile_path ?: ImageNotFound}"
                )
            )
        }
    }
    return persons
}

fun toPersonDetail(person: Person, personDetailDTO: PersonDetailDTO?): Person {
    person.adult = personDetailDTO?.adult ?: false
    person.biography = personDetailDTO?.biography ?: ""
    person.birthday = personDetailDTO?.birthday ?: ""
    person.deathday = personDetailDTO?.deathday ?: ""
    person.gender = personDetailDTO?.gender ?: 0
    person.homepage = personDetailDTO?.homepage ?: ""
    person.id = personDetailDTO?.id ?: 0
    person.imdb_id = personDetailDTO?.imdb_id ?: ""
    person.known_for_department = personDetailDTO?.known_for_department ?: ""
    person.name = personDetailDTO?.name ?: ""
    person.place_of_birth = personDetailDTO?.place_of_birth ?: ""
    person.popularity = personDetailDTO?.popularity ?: 0.0
    return person
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
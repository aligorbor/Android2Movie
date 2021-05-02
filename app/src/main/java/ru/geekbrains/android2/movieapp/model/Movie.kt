package ru.geekbrains.android2.movieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val budget: Int = 0,
    val genres: String = "",
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: Int = 0,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val status: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0,
    val duration: Int = 0,
    val revenue: Int = 0

) : Parcelable

fun getWorldMoviesNowPlaying(): List<Movie> {
    return listOf(
        Movie(title = "Godzilla vs. Kong", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Mortal Kombat", release_date = "2018-05-28", vote_average = 8.3),
        Movie(title = "Vanquish", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "The Marksman", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Twist", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Breach", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Ashfall", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "The Unholy", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Chaos Walking", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Raya and the Last Dragon", release_date = "2020-05-28", vote_average = 8.3),
    )
}

fun getRussianMoviesNowPlaying(): List<Movie> {
    return listOf(
        Movie(title = "Годзилла против Конга", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Мортал Комбатt", release_date = "2018-05-28", vote_average = 8.3),
        Movie(title = "Нечестивые", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Брешь", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Охотник на монстров", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Земля кочевников", release_date = "2020-05-28", vote_average = 8.3),
        Movie(
            title = "Ганзель, Гретель и Агентство Магии", release_date = "2020-05-28",
            vote_average = 8.3
        ),
        Movie(title = "Приколисты в дороге", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Афера Оливера Твиста", release_date = "2020-05-28", vote_average = 8.3),
        Movie(title = "Райя и последний дракон", release_date = "2020-05-28", vote_average = 8.3),
    )
}
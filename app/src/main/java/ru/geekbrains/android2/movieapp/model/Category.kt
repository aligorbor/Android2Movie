package ru.geekbrains.android2.movieapp.model

data class Category(
    val name: String = "",
    val movies: List<Movie>
)

fun getWorldCategories(): List<Category> {
    return listOf(
        Category(name = "Now Playing", movies = getWorldMoviesNowPlaying()),
        Category(name = "Popular", movies = getWorldMoviesPopular()),
        Category(name = "Top Rated", movies = getWorldMoviesTopRated()),
        Category(name = "Upcoming", movies = getWorldMoviesUpcoming()),
    )
}

fun getRussianCategories(): List<Category> {
    return listOf(
        Category(name = "В кинотеатрах", movies = getRussianMoviesNowPlaying()),
        Category(name = "Популярные", movies = getRussianMoviesPopular()),
        Category(name = "С высоким рейтингом", movies = getRussianMoviesTopRated()),
        Category(name = "Выходящие в прокат", movies = getRussianMoviesUpcoming()),
    )
}
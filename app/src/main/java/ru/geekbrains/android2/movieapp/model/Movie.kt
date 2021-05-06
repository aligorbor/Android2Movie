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
        Movie(title = "Godzilla vs. Kong", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
        original_title = "Godzilla vs. Kong",vote_count = 43245, duration = 135,
        overview = "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages."),
        Movie(title = "Mortal Kombat", release_date = "2018-05-28", vote_average = 7.5,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "Vanquish", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "The Marksman", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "Twist", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "Breach", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "Ashfall", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "The Unholy", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "Chaos Walking", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
        Movie(title = "Raya and the Last Dragon", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "fantasy, action movie, thriller",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."),
    )
}

fun getRussianMoviesNowPlaying(): List<Movie> {
    return listOf(
        Movie(title = "Годзилла против Конга", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Godzilla vs. Kong",vote_count = 43245, duration = 135,
            overview ="Конг и группа ученых отправляются в опасное путешествие в поисках родного дома гиганта. Среди них девочка Джия, единственная, кто умеет общаться с Конгом. Неожиданно они сталкиваются с разъяренным Годзиллой, разрушающим все на своем пути. Битва двух титанов, спровоцированная неведомыми силами — лишь малая часть тайны, спрятанной в недрах Земли."),
        Movie(title = "Мортал Комбатt", release_date = "2018-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(title = "Нечестивые", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(title = "Брешь", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(title = "Охотник на монстров", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(title = "Земля кочевников", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(
            title = "Ганзель, Гретель и Агентство Магии", release_date = "2020-05-28",
            vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(title = "Приколисты в дороге", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(title = "Афера Оливера Твиста", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд."),
        Movie(title = "Райя и последний дракон", release_date = "2020-05-28", vote_average = 8.3,
            budget =233445500, revenue = 1233445643, genres = "фэнтези, боевик, триллер",
            original_title = "Mortal Kombat",vote_count = 43245, duration = 135,
            overview ="Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд.")
        )
}

fun getWorldMoviesPopular(): List<Movie> {
    return getWorldMoviesNowPlaying()
}

fun getRussianMoviesPopular(): List<Movie> {
    return getRussianMoviesNowPlaying()
}

fun getWorldMoviesTopRated(): List<Movie> {
    return getWorldMoviesNowPlaying()
}

fun getRussianMoviesTopRated(): List<Movie> {
    return getRussianMoviesNowPlaying()
}

fun getWorldMoviesUpcoming(): List<Movie> {
    return getWorldMoviesNowPlaying()
}

fun getRussianMoviesUpcoming(): List<Movie> {
    return getRussianMoviesNowPlaying()
}


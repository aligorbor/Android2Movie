package ru.geekbrains.android2.movieapp.model

import ru.geekbrains.android2.movieapp.BuildConfig
import ru.geekbrains.android2.movieapp.interactors.StringsInteractor
import ru.geekbrains.android2.movieapp.model.database.Database
import ru.geekbrains.android2.movieapp.model.database.FavoriteEntity
import ru.geekbrains.android2.movieapp.model.database.HistoryEntity
import ru.geekbrains.android2.movieapp.model.rest.*
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.CategoryDTO

class RepositoryImpl : Repository {

    override fun getCategoriesFromRemoteStorage(isRus: Boolean, interactor: StringsInteractor) =
        loadCategories(isRus, interactor)

    override fun getMovieDetailFromRemoteStorage(movie: Movie) = loadMovieDetail(movie)

    private fun loadCategories(
        isRus: Boolean,
        interactor: StringsInteractor
    ): MutableList<Category> {
        val page = 1
        val lang = if (isRus) languageRU
        else languageEN
        return mutableListOf(
            Category(
                name = interactor.strNowPlaying,
                movies = toMovies(
                    loadCategory(categoryNowPlaying, lang, page)
                ).apply {
                    map { it.isFavorite = isFavorite(it.id) }
                },
                id = 0
            ),
            Category(
                name = interactor.strPopular,
                movies = toMovies(
                    loadCategory(categoryPopular, lang, page)
                ).apply {
                    map { it.isFavorite = isFavorite(it.id) }
                },
                id = 1
            ),
            Category(
                name = interactor.strTopRated,
                movies = toMovies(
                    loadCategory(categoryTopRated, lang, page)
                ).apply {
                    map { it.isFavorite = isFavorite(it.id) }
                },
                id = 2
            ),
            Category(
                name = interactor.strUpcoming,
                movies = toMovies(
                    loadCategory(categoryUpComing, lang, page)
                ).apply {
                    map { it.isFavorite = isFavorite(it.id) }
                },
                id = 3
            )
        )
    }

    private fun loadCategory(categoryName: String, language: String, page: Int): CategoryDTO? =
        BackendRepo.api.getCategory(categoryName, BuildConfig.MOVIE_API_KEY, language, page)
            .execute()
            .body()

    private fun loadMovieDetail(movie: Movie): Movie {
        val lang = if (movie.isRus) languageRU
        else languageEN
        return toMovieDetail(
            movie, BackendRepo.api.getMovieDetail(movie.id, BuildConfig.MOVIE_API_KEY, lang)
                .execute()
                .body()
        )
    }

    override fun getAllHistory(): List<Movie> =
        convertHistoryEntitiesToMovies(Database.db.historyDao().all())

    override fun saveToHistory(movie: Movie) =
        Database.db.historyDao().insert(convertMovieToHistoryEntity(movie))

    override fun getAllFavorite(): List<Movie> =
        convertFavoriteEntitiesToMovies(Database.db.favoriteDao().all())

    override fun saveToFavorite(movie: Movie) {
        Database.db.favoriteDao().insert(convertMovieToFavoriteEntity(movie))
    }

    override fun deleteFromFavorite(id: Int) = Database.db.favoriteDao().deleteById(id)

    override fun isFavorite(id: Int): Boolean = Database.db.favoriteDao().countById(id) > 0

    override fun getNote(id: Int): String {
        val listOfEntity = Database.db.historyDao().getDataById(id)
        return if (listOfEntity.isNotEmpty())
            listOfEntity[0].note
        else
            ""
    }

    private fun convertFavoriteEntitiesToMovies(entityList: List<FavoriteEntity>): List<Movie> =
        entityList.map {
            Movie(
                id = it.id.toInt(), title = it.title, release_date = it.release_date,
                vote_average = it.vote_average, poster_path = it.poster_path
            )
        }

    private fun convertHistoryEntitiesToMovies(entityList: List<HistoryEntity>): List<Movie> =
        entityList.map {
            Movie(
                id = it.id.toInt(), title = it.title, release_date = it.release_date,
                vote_average = it.vote_average, poster_path = it.poster_path, note = it.note
            )
        }

    private fun convertMovieToHistoryEntity(movie: Movie): HistoryEntity =
        HistoryEntity(
            id = movie.id.toLong(),
            title = movie.title,
            release_date = movie.release_date,
            vote_average = movie.vote_average,
            poster_path = movie.poster_path,
            note = movie.note
        )

    private fun convertMovieToFavoriteEntity(movie: Movie): FavoriteEntity =
        FavoriteEntity(
            id = movie.id.toLong(),
            title = movie.title,
            release_date = movie.release_date,
            vote_average = movie.vote_average,
            poster_path = movie.poster_path
        )
}
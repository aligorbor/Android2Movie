package ru.geekbrains.android2.movieapp.model

import ru.geekbrains.android2.movieapp.BuildConfig
import ru.geekbrains.android2.movieapp.interactors.StringsInteractor
import ru.geekbrains.android2.movieapp.model.rest.*
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.CategoryDTO

class RepositoryImpl : Repository {

    override fun getCategoriesFromRemoteStorage(isRus: Boolean, interactor: StringsInteractor) =
        loadCategories(isRus, interactor)

    override fun getMovieDetailFromRemoteStorage(movie: Movie) = loadMovieDetail(movie)

    private fun loadCategories(isRus: Boolean, interactor: StringsInteractor): MutableList<Category>{
        val page = 1
        val lang = if (isRus) languageRU
        else languageEN
        return mutableListOf(
            Category(
                name = interactor.strNowPlaying,
                movies = toMovies(
                    loadCategory(categoryNowPlaying,lang,page)
                ),
                id = 0
            ),
            Category(
                name = interactor.strPopular,
                movies = toMovies(
                    loadCategory(categoryPopular,lang,page)
                ),
                id = 1
            ),
            Category(
                name = interactor.strTopRated,
                movies = toMovies(
                    loadCategory(categoryTopRated,lang,page)
                ),
                id = 2
            ),
            Category(
                name = interactor.strUpcoming,
                movies = toMovies(
                    loadCategory(categoryUpComing,lang,page)
                ),
                id = 3
            )
        )
    }

    private fun loadCategory(categoryName:String,language:String,page:Int):CategoryDTO?=
        BackendRepo.api.getCategory(categoryName, BuildConfig.MOVIE_API_KEY,language,page)
            .execute()
            .body()

    private fun loadMovieDetail(movie:Movie):Movie{
        val lang = if (movie.isRus) languageRU
        else languageEN
        return toMovieDetail(movie,BackendRepo.api.getMovieDetail(movie.id, BuildConfig.MOVIE_API_KEY,lang)
            .execute()
            .body())
    }
}
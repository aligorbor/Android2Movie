package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.geekbrains.android2.movieapp.interactors.StringsInteractor
import ru.geekbrains.android2.movieapp.model.Category
import ru.geekbrains.android2.movieapp.model.Movie
import ru.geekbrains.android2.movieapp.model.RepositoryImpl


class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

    fun getLiveData() = liveDataToObserve

    fun getCategoriesFromRemoteSource(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean
    ) =
        getDataFromRemoteSource(isRus, interactor, adult)

    fun getMoviesHistory() {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.SuccessCategory(
                        Category(movies = repositoryImpl.getAllHistory().toMutableList())
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    fun getMoviesFavorite() {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.SuccessCategory(
                        Category(movies = repositoryImpl.getAllFavorite().toMutableList())
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    fun saveMovieToFavorite(movie: Movie) {
        launch(Dispatchers.IO) {
            try {
                repositoryImpl.saveToFavorite(movie)
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    fun deleteMovieFromFavorite(movie: Movie) {
        launch(Dispatchers.IO) {
            try {
                repositoryImpl.deleteFromFavorite(movie.id)
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    private fun getDataFromRemoteSource(
        isRussian: Boolean,
        interactor: StringsInteractor,
        adult: Boolean
    ) {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.Success(
                        repositoryImpl.getCategoriesFromRemoteStorage(isRussian, interactor, adult)
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }
}
package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2.movieapp.model.Movie
import ru.geekbrains.android2.movieapp.model.RepositoryImpl

class DetailsViewModel(
    private val liveDataToObserve: MutableLiveData<AppStateDetails> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMovieDetailFromRemoteSource(movie: Movie) = getDataFromRemoteSource(movie)

    private fun getDataFromRemoteSource(movie: Movie) {
        liveDataToObserve.value = AppStateDetails.Loading
        Thread {
            try {
                liveDataToObserve.postValue(
                    AppStateDetails.Success(
                        repositoryImpl.getMovieDetailFromRemoteStorage(movie)
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppStateDetails.Error(e))
            }
        }.start()
    }
}
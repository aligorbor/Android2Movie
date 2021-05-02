package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2.movieapp.model.RepositoryImpl
import ru.geekbrains.android2.movieapp.viewmodel.AppState

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMoviesFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getMoviesFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getMoviesFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian)
                        repositoryImpl.getMoviesNowPlayingFromLocalStorageRus() else
                        repositoryImpl.getMoviesNowPlayingFromLocalStorageWorld()
                )
            )
            //       liveDataToObserve.postValue(AppState.Error(Throwable("Test error")))
        }.start()
    }
}
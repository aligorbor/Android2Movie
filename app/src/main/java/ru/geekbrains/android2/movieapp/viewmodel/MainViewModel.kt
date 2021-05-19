package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2.movieapp.interactors.StringsInteractor
import ru.geekbrains.android2.movieapp.model.RepositoryImpl


class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getCategoriesFromRemoteSource(isRus: Boolean, interactor: StringsInteractor) =
        getDataFromRemoteSource(isRus, interactor)

    private fun getDataFromRemoteSource(isRussian: Boolean, interactor: StringsInteractor) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            try {
                liveDataToObserve.postValue(
                    AppState.Success(
                        repositoryImpl.getCategoriesFromRemoteStorage(isRussian, interactor)
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }.start()
    }
}
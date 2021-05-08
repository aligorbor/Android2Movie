package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android2.movieapp.model.RepositoryImpl
import ru.geekbrains.android2.movieapp.viewmodel.AppState

const val timeoutSleep :Long  = 1000

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getCategoriesFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getCategoriesFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getCategoriesFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(timeoutSleep)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian)
                        repositoryImpl.getCategoriesFromLocalStorageRus() else
                        repositoryImpl.getCategoriesFromLocalStorageWorld()
                )
            )
            //Проверить showSnackBar:
            //      liveDataToObserve.postValue(AppState.Error(Throwable("Test error")))
        }.start()
    }
}
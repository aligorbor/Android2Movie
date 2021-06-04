package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.geekbrains.android2.movieapp.model.RepositoryImpl


class PeoplesViewModel(
    private val liveDataToObserve: MutableLiveData<AppStatePeoples> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

    fun getLiveData() = liveDataToObserve

    fun getPeoplesFromRemoteSource(
        isRus: Boolean,
        adult: Boolean,
        page: Int
    ) = getDataFromRemoteSource(isRus, adult, page)

    private fun getDataFromRemoteSource(
        isRussian: Boolean,
        adult: Boolean,
        page: Int
    ) {
        liveDataToObserve.value = AppStatePeoples.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppStatePeoples.Success(
                        repositoryImpl.getPersonsPopularFromRemoteStorage(isRussian, adult, page)
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppStatePeoples.Error(e))
            }
        }
    }
}
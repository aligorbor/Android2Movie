package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.geekbrains.android2.movieapp.model.Person
import ru.geekbrains.android2.movieapp.model.RepositoryImpl

class DetailsPeopleViewModel(
    private val liveDataToObserve: MutableLiveData<AppStateDetailsPeople> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

    fun getLiveData() = liveDataToObserve

    fun getPeopleDetailFromRemoteSource(person: Person) = getDataFromRemoteSource(person)

    private fun getDataFromRemoteSource(person: Person) {
        liveDataToObserve.value = AppStateDetailsPeople.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppStateDetailsPeople.Success(
                        repositoryImpl.getPersonDetailFromRemoteStorage(person)
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppStateDetailsPeople.Error(e))
            }
        }
    }
}
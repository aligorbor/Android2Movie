package ru.geekbrains.android2.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.geekbrains.android2.movieapp.interactors.StringsInteractor
import ru.geekbrains.android2.movieapp.model.RepositoryImpl

class CategoryViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {
    fun getLiveData() = liveDataToObserve
    fun getCategoryByIdFromRemoteSource(
        isRussian: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int,
        id: Int
    ) {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.SuccessCategoryById(
                        repositoryImpl.getCategoryByIdFromRemoteStorage(
                            isRussian,
                            interactor,
                            adult,
                            page,
                            id
                        )
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }
}
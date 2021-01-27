package kz.kazpost.unloadingarea.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.*
import retrofit2.Response

open class LoadingViewModel : ViewModel() {
    private val _isLoadingLiveData = MutableLiveData<LoadingStatus>()
    val isLoadingLiveData: LiveData<LoadingStatus> = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    enum class LoadingStatus {
        LOADING(),
        NOT_LOADING()
    }

    protected fun <T> loadFlow(
        flow: Flow<Response<T>>,
        loadingStatusReceivingLiveData: MutableLiveData<LoadingStatus> = _isLoadingLiveData,
        errorReceivingLiveData: MutableLiveData<String> = _errorLiveData
    ): LiveData<T?> {
        return flow
            .onStart {
                loadingStatusReceivingLiveData.postValue(LoadingStatus.LOADING)
            }
            .map {
                if (it.isSuccessful) {
                    it.body()
                } else {
                    throw Exception(it.errorBody()?.string())
                }
            }
            .catch {
                emit(null)
                errorReceivingLiveData.postValue(it.localizedMessage)
            }
            .onCompletion {
                loadingStatusReceivingLiveData.postValue(LoadingStatus.NOT_LOADING)
            }
            .asLiveData()
    }
}
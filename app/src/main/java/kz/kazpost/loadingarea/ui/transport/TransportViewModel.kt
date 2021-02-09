package kz.kazpost.loadingarea.ui.transport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.kazpost.loadingarea.base.LoadingViewModel
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.ui.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class TransportViewModel @Inject constructor(
    private val repository: TransportRepository,
    private val authRepository: AuthRepository
) : LoadingViewModel() {
    private val _transportListLiveData = MediatorLiveData<List<TransportModel>>()
    val transportLiveData: LiveData<List<TransportModel>> = _transportListLiveData

    fun loadTransportList() {
        val result = loadFlow(repository.getTransportList(), onRetry = this::loadTransportList)
        _transportListLiveData.observeOnce(result)
    }

    fun exitUser() {
        authRepository.exitUser()
    }
}
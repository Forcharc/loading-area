package kz.kazpost.unloadingarea.ui.transport

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kz.kazpost.unloadingarea.base.LoadingViewModel
import kz.kazpost.unloadingarea.ui.auth.AuthRepository

class TransportViewModel @ViewModelInject constructor(
    private val repository: TransportRepository,
    private val authRepository: AuthRepository
) : LoadingViewModel() {
    private val _transportListLiveData = MediatorLiveData<List<TransportModel>>()
    val transportLiveData: LiveData<List<TransportModel>> = _transportListLiveData

    fun loadTransportList() {
        val result = loadFlow(repository.getTransportList(), onRetry = this::loadTransportList)
        _transportListLiveData.observeFirstValueFromLiveDataAndUnsubscribe(result)
    }

    fun exitUser() {
        authRepository.exitUser()
    }
}
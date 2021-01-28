package kz.kazpost.unloadingarea.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kazpost.unloadingarea.base.LoadingViewModel
import kz.kazpost.unloadingarea.data.models.TransportModel
import kz.kazpost.unloadingarea.repositories.TransportRepository

class TransportViewModel @ViewModelInject constructor(
    private val repository: TransportRepository
) : LoadingViewModel() {
    private val _transportListLiveData = MediatorLiveData<List<TransportModel>>()
    val transportLiveData: LiveData<List<TransportModel>> = _transportListLiveData

    fun loadTransportList() {
        val result = loadFlow(repository.getTransportList())
        _transportListLiveData.addSource(result) {
            _transportListLiveData.postValue(it)
            _transportListLiveData.removeSource(result)
        }
    }
}
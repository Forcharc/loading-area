package kz.kazpost.unloadingarea.ui.add_s_invoice

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kz.kazpost.unloadingarea.base.LoadingViewModel


class AddSInvoiceViewModel @ViewModelInject constructor(private val repository: AddSInvoiceRepository) : LoadingViewModel() {
    private lateinit var tInvoiceNumber: String

    private val _sInvoiceListLiveData = MediatorLiveData<List<SInvoiceModel>>()
    val sInvoiceListLiveData: LiveData<List<SInvoiceModel>> = _sInvoiceListLiveData

    fun init(tInvoiceNumber: String) {
        this.tInvoiceNumber = tInvoiceNumber
    }

    fun loadSInvoices() {
        val result = loadFlow(repository.getAvailableSInvoices(), onRetry = this::loadSInvoices)
        _sInvoiceListLiveData.observeFirstValueFromLiveDataAndUnsubscribe(result)
    }

}
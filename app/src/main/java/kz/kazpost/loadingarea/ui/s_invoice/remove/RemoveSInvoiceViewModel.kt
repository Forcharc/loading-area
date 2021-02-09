package kz.kazpost.loadingarea.ui.s_invoice.remove

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.kazpost.loadingarea.base.LoadingViewModel
import kz.kazpost.loadingarea.ui._models.SInvoiceModel
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceRepository
import javax.inject.Inject

@HiltViewModel
class RemoveSInvoiceViewModel @Inject constructor(private val repository: SInvoiceRepository): LoadingViewModel() {
    private lateinit var tInvoiceNumber: String
    private val _sInvoiceListLiveData = MediatorLiveData<List<SInvoiceModel>>()
    val sInvoiceListLiveData: LiveData<List<SInvoiceModel>> = _sInvoiceListLiveData

    fun init(tInvoiceNumber: String) {
        this.tInvoiceNumber = tInvoiceNumber
    }
}
package kz.kazpost.loadingarea.ui.s_invoice.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.kazpost.loadingarea.base.LoadingViewModel
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceModel
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceRepository
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AddSInvoiceViewModel @Inject constructor(private val repository: SInvoiceRepository) :
    LoadingViewModel() {
    private var tInvoiceId by Delegates.notNull<Int>()
    private lateinit var tInvoiceNumber: String
    private lateinit var notYetVisitedDepartments: List<String>

    private val _sInvoiceListLiveData = MediatorLiveData<List<SInvoiceModel>>()
    val sInvoiceListLiveData: LiveData<List<SInvoiceModel>> = _sInvoiceListLiveData

    fun init(tInvoiceId: Int, tInvoiceNumber: String, notYetVisitedDepartments: List<String>) {
        this.tInvoiceId = tInvoiceId
        this.tInvoiceNumber = tInvoiceNumber
        this.notYetVisitedDepartments = notYetVisitedDepartments
    }

    fun loadSInvoices() {
        val result = loadFlow(
            repository.getAvailableSInvoices(notYetVisitedDepartments),
            onRetry = this::loadSInvoices
        )
        _sInvoiceListLiveData.observeOnce(result)
    }

}
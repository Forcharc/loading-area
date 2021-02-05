package kz.kazpost.loadingarea.ui.s_invoice

import kotlinx.coroutines.flow.Flow
import kz.kazpost.loadingarea.repositories.models.ResultResponse
import retrofit2.Response

interface SInvoiceRepository {
    fun getAvailableSInvoices(notYetVisitedDepartments: List<String>): Flow<Response<List<SInvoiceModel>>>


    fun addSInvoicesToTInvoice(sInvoices: List<Int>, tInvoiceId: Int): Flow<Response<Boolean>>

}
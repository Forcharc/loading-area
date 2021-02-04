package kz.kazpost.loadingarea.ui.s_invoice

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SInvoiceRepository {
    fun getAvailableSInvoices(notYetVisitedDepartments: List<String>): Flow<Response<List<SInvoiceModel>>>

    fun getSInvoicesAddedToTInvoice(tInvoiceNumber: String): Flow<Response<List<SInvoiceModel>>>

    fun addSInvoiceToTInvoice(sInvoiceModel: SInvoiceModel, tInvoiceNumber: String)

    fun removeSInvoices(sInvoiceNumbers: List<String>)

}
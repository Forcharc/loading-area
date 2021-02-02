package kz.kazpost.unloadingarea.ui.add_s_invoice

import kotlinx.coroutines.flow.Flow
import kz.kazpost.unloadingarea.ui.transport.TransportModel
import retrofit2.Response

interface AddSInvoiceRepository {
    fun getAvailableSInvoices(): Flow<Response<List<SInvoiceModel>>>
}
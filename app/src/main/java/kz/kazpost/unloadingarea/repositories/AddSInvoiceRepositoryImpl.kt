package kz.kazpost.unloadingarea.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kz.kazpost.unloadingarea.ui.add_s_invoice.AddSInvoiceRepository
import kz.kazpost.unloadingarea.ui.add_s_invoice.SInvoiceModel
import retrofit2.Response
import javax.inject.Inject


class AddSInvoiceRepositoryImpl @Inject constructor() : AddSInvoiceRepository {
    override fun getAvailableSInvoices(): Flow<Response<List<SInvoiceModel>>> {
        return flowOf(Response.success(200, emptyList()))
    }
}
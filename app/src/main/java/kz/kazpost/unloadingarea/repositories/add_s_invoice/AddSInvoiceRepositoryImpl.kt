package kz.kazpost.unloadingarea.repositories.add_s_invoice

import kotlinx.coroutines.flow.*
import kz.kazpost.unloadingarea.database.UserPreferences
import kz.kazpost.unloadingarea.ui.add_s_invoice.AddSInvoiceRepository
import kz.kazpost.unloadingarea.ui.add_s_invoice.SInvoiceModel
import retrofit2.Response
import javax.inject.Inject


class AddSInvoiceRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
    private val api: SInvoiceApi
) :
    AddSInvoiceRepository {
    override fun getAvailableSInvoices(): Flow<Response<List<SInvoiceModel>>> {
        return flowOf(Response.success(200, emptyList()))

/*
        return flow {
            emit(
                api.getAvailableSInvoices(userPreferences.userLogin, userPreferences.userDepartmentId)
            )
        }.map { response ->
            response.transform {
                ResponseToModelMappers.transportListResponseToTransportModelList(it!!)
            }
        }.flowOn(Dispatchers.IO)
*/
    }
}
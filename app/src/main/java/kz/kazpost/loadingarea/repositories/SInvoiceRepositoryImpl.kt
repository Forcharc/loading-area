package kz.kazpost.loadingarea.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kz.kazpost.loadingarea.api.SInvoiceApi
import kz.kazpost.loadingarea.api._requests.AddSInvoicesToTInvoiceRequest
import kz.kazpost.loadingarea.api._requests.GetAvailableSInvoicesRequest
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.repositories._mappers.SInvoiceMappers.toSInvoiceModel
import kz.kazpost.loadingarea.ui._models.SInvoiceModel
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceRepository
import kz.kazpost.loadingarea.util.DateUtils
import kz.kazpost.loadingarea.util.extentions.transformBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


class SInvoiceRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
    private val api: SInvoiceApi,
) : SInvoiceRepository {

    override fun getAvailableSInvoices(notYetVisitedDepartments: List<String>): Flow<Response<List<SInvoiceModel>>> {
        val yesterdayDate = DateUtils.getYesterdayDate()
        val todayDate = DateUtils.getTodayDate()
        val login = userPreferences.userLogin
        val department = userPreferences.userDepartmentId
        if (department != null && login != null) {
            val request = GetAvailableSInvoicesRequest(
                yesterdayDate,
                notYetVisitedDepartments,
                todayDate,
                login,
                department
            )
            return flow {
                emit(
                    api.getAvailableSInvoices(request)
                )
            }.map { response ->
                response.transformBody { list ->
                    list?.map { it.toSInvoiceModel() } ?: emptyList()
                }
            }.flowOn(Dispatchers.IO)
        } else {
            return flowOf(
                Response.error(
                    500,
                    ResponseBody.create(null, "Can not get user login and department")
                )
            )
        }

    }


    override fun addSInvoicesToTInvoice(
        sInvoices: List<Int>,
        tInvoiceId: Int
    ): Flow<Response<Boolean>> {
        val userLogin = userPreferences.userLogin
        if (userLogin != null) {
            val request = AddSInvoicesToTInvoiceRequest(
                sInvoices.joinToString { it.toString() },
                tInvoiceId,
                0,
                userLogin
            )
            return flow {
                emit(
                    api.addSInvoiceToTInvoice(request)
                )
            }.map { response ->
                response.transformBody {  it?.isSuccessful() ?: false }
            }
                .flowOn(Dispatchers.IO)
        } else {
            return flowOf(
                Response.error(
                    500,
                    ResponseBody.create(null, "Can not get user login")
                )
            )
        }
    }


}
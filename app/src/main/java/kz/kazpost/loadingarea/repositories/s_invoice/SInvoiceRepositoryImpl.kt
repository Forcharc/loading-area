package kz.kazpost.loadingarea.repositories.s_invoice

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.repositories.s_invoice.models.GetAvailableSInvoicesRequest
import kz.kazpost.loadingarea.repositories.s_invoice.models.Mappers.toSInvoiceDBModel
import kz.kazpost.loadingarea.repositories.s_invoice.models.Mappers.toSInvoiceModel
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceRepository
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceModel
import kz.kazpost.loadingarea.util.DateUtils
import kz.kazpost.loadingarea.util.extentions.transform
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


class SInvoiceRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
    private val api: SInvoiceApi,
    private val dao: SInvoiceDao
) :
    SInvoiceRepository {
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
                response.transform { list ->
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

    override fun getSInvoicesAddedToTInvoice(tInvoiceNumber: String): Flow<Response<List<SInvoiceModel>>> {
        return flow {
            emit(
                Response.success(200, dao.getThoseInTInvoice(tInvoiceNumber)).transform { list ->
                    if (list.isNullOrEmpty()) {
                        emptyList()
                    } else {
                        list.map {
                            it.toSInvoiceModel()
                        }
                    }
                }
            )
        }
    }

    override fun addSInvoiceToTInvoice(sInvoiceModel: SInvoiceModel, tInvoiceNumber: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val sInvoiceInDB = dao.getSInvoiceByNumber(sInvoiceModel.number)
            val sInvoiceAlreadyAddedToAnotherTInvoice =
                sInvoiceInDB != null && sInvoiceInDB.tInvoiceNumber != tInvoiceNumber
            if (sInvoiceAlreadyAddedToAnotherTInvoice) {
                // TODO remove all parcels from this invoice from db
            }
            dao.addSInvoice(sInvoiceModel.toSInvoiceDBModel(tInvoiceNumber))
        }
    }

    override fun removeSInvoices(sInvoiceNumbers: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteByNumbers(sInvoiceNumbers)
            // TODO remove all parcels from this invoices from db
        }
    }
}
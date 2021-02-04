package kz.kazpost.loadingarea.repositories.transport

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kz.kazpost.loadingarea.ui.transport.TransportModel
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.repositories.transport.models.Mappers
import kz.kazpost.loadingarea.ui.transport.TransportRepository
import kz.kazpost.loadingarea.util.DateUtils
import kz.kazpost.loadingarea.util.extentions.transform
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class TransportRepositoryImpl @Inject constructor(
    private val api: TransportApi,
    private val prefs: UserPreferences
) : TransportRepository {

    override fun getTransportList(): Flow<Response<List<TransportModel>>> {
        val userDepartmentId = prefs.userDepartmentId
        return if (userDepartmentId.isNullOrBlank()) {
            createNoDepartmentErrorResponse()
        } else {
            getTransportListFromServer(userDepartmentId)
        }
    }

    private fun getTransportListFromServer(userDepartmentId: String): Flow<Response<List<TransportModel>>> {
        val yesterdayDate = DateUtils.getYesterdayDate()
        return flow {
            emit(
                api.getTransportList(
                    userDepartmentId,
                    yesterdayDate
                )
            )
        }.map { response ->
            response.transform {
                Mappers.transportListResponseToTransportModelList(userDepartmentId, it!!)
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun createNoDepartmentErrorResponse(): Flow<Response<List<TransportModel>>> =
        flow {
            emit(
                Response.error<List<TransportModel>>(
                    500,
                    ResponseBody.create(null, "Logged in user has no department")
                )
            )
        }
}
package kz.kazpost.unloadingarea.repositories.transport

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kz.kazpost.unloadingarea.ui.transport.TransportModel
import kz.kazpost.unloadingarea.database.UserPreferences
import kz.kazpost.unloadingarea.repositories.transport.models.Mappers
import kz.kazpost.unloadingarea.ui.transport.TransportRepository
import kz.kazpost.unloadingarea.util.extentions.transform
import okhttp3.ResponseBody
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
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
        val yesterdayDate = getYesterdayDate()
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


    private fun getYesterdayDate(): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -1)
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale("RU"))
        return sdf.format(date.time)
    }
}
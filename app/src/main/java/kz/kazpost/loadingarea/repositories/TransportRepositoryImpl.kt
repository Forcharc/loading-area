package kz.kazpost.loadingarea.repositories

import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.api.TransportApi
import kz.kazpost.loadingarea.base.LoadingViewModel
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.repositories._mappers.TransportMappers
import kz.kazpost.loadingarea.ui._adapters.TransportAdapter
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.ui._models.WorkerInfoModel
import kz.kazpost.loadingarea.ui.transport.ALLOWED
import kz.kazpost.loadingarea.ui.transport.ActionPermissionResult
import kz.kazpost.loadingarea.ui.transport.DENIED
import kz.kazpost.loadingarea.ui.transport.TransportRepository
import kz.kazpost.loadingarea.util.DateUtils
import kz.kazpost.loadingarea.util.extentions.transformBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class TransportRepositoryImpl @Inject constructor(
    private val api: TransportApi,
    private val prefs: UserPreferences
) : TransportRepository {


    override fun checkActionPermissionForUser(
        actionType: TransportAdapter.TransportActionType,
        transportModel: TransportModel
    ): Flow<Response<ActionPermissionResult>> {
        return flow {
            emit(api.getWorkerInfoFotTInvoice(transportModel.tInvoiceNumber, prefs.userLogin ?: ""))
        }.map {
            it.transformBody { response ->
                val department = if (response?.transportInfo?.status == "Arrived") {
                    response?.transportInfo?.fromDepartment?.name
                } else {
                    null
                }
                val worker =
                    if (response?.transportInfo?.worker.isNullOrBlank()) null else response?.transportInfo?.worker
                WorkerInfoModel(
                    worker,
                    department,
                    response?.transportInfo?.status,
                    response?.userTransports?.map { it.transportListId ?: "" }
                        ?: emptyList()
                )
            }
        }.map { response ->
            response.transformBody { worterInfoModel ->
                if (worterInfoModel?.status == "Arrived") {
                    if (worterInfoModel?.transportDepartment == prefs.userDepartmentId) {
                        if (worterInfoModel?.workerLogin == prefs.userLogin) {
                            ALLOWED(actionType, transportModel)
                        } else {
                            if (worterInfoModel?.workerLogin == null) {
                                if (worterInfoModel?.workerCurrentTInvoiceNumbers.isEmpty()) {
                                    ALLOWED(actionType, transportModel)
                                } else {
                                    DENIED(
                                        LoadingViewModel.StringResource(
                                            R.string.error_wrong_t_invoice,
                                            worterInfoModel.workerCurrentTInvoiceNumbers.joinToString { it }
                                        )
                                    )
                                }
                            } else {
                                DENIED(
                                    LoadingViewModel.StringResource(
                                        R.string.error_wrong_user,
                                        worterInfoModel?.workerLogin ?: "Unknown"
                                    )
                                )
                            }
                        }
                    } else {
                        DENIED(LoadingViewModel.StringResource(R.string.error_wrong_department))
                    }
                } else {
                    DENIED(LoadingViewModel.StringResource(R.string.error_wrong_status))
                }
            }
        }
    }


    override fun getTransportPagingSource(): TransportRepository.TransportPagingSource {
        return TransportPagingSource()
    }


    inner class TransportPagingSource : TransportRepository.TransportPagingSource() {
        private val userDepartmentId = prefs.userDepartmentId ?: "unknown user department"
        private val yesterdayDate = DateUtils.getYesterdayDate()

        override fun getRefreshKey(state: PagingState<Int, TransportModel>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransportModel> {
            try {
                // Start refresh at page 1 if undefined.
                val nextPageNumber = params.key ?: 1
                val response = api.getTransportList(
                    userDepartmentId,
                    yesterdayDate,
                    nextPageNumber
                )
                val transportModelList = TransportMappers.transportListResponseToTransportModelList(
                    prefs.userLogin ?: "",
                    userDepartmentId,
                    response.body()?.transportList ?: emptyList()
                )

                return LoadResult.Page(
                    data = transportModelList,
                    prevKey = if (nextPageNumber >= 2) nextPageNumber - 1 else null, // Only paging forward.
                    nextKey = if (response.body()?.transportList?.size != 0) (nextPageNumber + 1) else null
                )
            } catch (e: IOException) {
                // IOException for network failures.
                return LoadResult.Error(e)
            } catch (e: HttpException) {
                // HttpException for any non-2xx HTTP status codes.
                return LoadResult.Error(e)
            }
        }
    }
}
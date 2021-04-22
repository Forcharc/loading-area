package kz.kazpost.loadingarea.ui.transport

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kz.kazpost.loadingarea.ui._adapters.TransportAdapter
import kz.kazpost.loadingarea.ui._models.TransportModel
import retrofit2.Response

interface TransportRepository {
    fun getTransportPagingSource(): TransportPagingSource

    fun checkActionPermissionForUser(
        actionType: TransportAdapter.TransportActionType,
        transportModel: TransportModel
    ): Flow<Response<ActionPermissionResult>>

    abstract class TransportPagingSource : PagingSource<Int, TransportModel>()
}
package kz.kazpost.loadingarea.ui.transport

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TransportRepository {
    fun getTransportList(): Flow<Response<List<TransportModel>>>
}
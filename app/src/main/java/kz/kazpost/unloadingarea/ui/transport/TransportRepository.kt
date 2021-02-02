package kz.kazpost.unloadingarea.ui.transport

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TransportRepository {
    fun getTransportList(): Flow<Response<List<TransportModel>>>
}
package kz.kazpost.unloadingarea.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kz.kazpost.unloadingarea.api.TransportApi
import kz.kazpost.unloadingarea.data.mappers.ResponseToModelMappers
import kz.kazpost.unloadingarea.data.models.TransportModel
import kz.kazpost.unloadingarea.data.requests.AuthRequest
import kz.kazpost.unloadingarea.util.transform
import retrofit2.Response
import javax.inject.Inject

class TransportRepository @Inject constructor(private val api: TransportApi) {

    fun getTransportList(): Flow<Response<List<TransportModel>>> {
        return flow { emit(api.getTransportList("019931", "26.01.2021")) }.map { response ->
            response.transform {
                ResponseToModelMappers.transportListResponseToTransportModelList(it!!)
            }
        }.flowOn(Dispatchers.IO)
    }
}
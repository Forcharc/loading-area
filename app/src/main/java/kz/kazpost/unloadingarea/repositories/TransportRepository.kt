package kz.kazpost.unloadingarea.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kz.kazpost.unloadingarea.data.models.TransportModel
import kz.kazpost.unloadingarea.data.requests.AuthRequest
import kz.kazpost.unloadingarea.util.transform
import retrofit2.Response
import javax.inject.Inject

class TransportRepository @Inject constructor() {
    // Test response
    fun getTransportList(): Flow<Response<List<TransportModel>>> {
        return flow {
            emit(
                Response.success(
                    200,
                    listOf(
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                        TransportModel("T23423423423", "AST", "NUR", "самолет"),
                    )
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}
package kz.kazpost.unloadingarea.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kz.kazpost.unloadingarea.api.AuthApi
import kz.kazpost.unloadingarea.api.requests.AuthRequest
import kz.kazpost.unloadingarea.api.responses.AuthResponse
import kz.kazpost.unloadingarea.util.transform
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: AuthApi) {

    fun authorize(login: String, password: String): Flow<Response<Boolean>> {
        return flow { emit(api.makeAuthorization(AuthRequest(login, password))) }.map { response ->
            response.transform {
                response.isSuccessful
            }
        }.flowOn(Dispatchers.IO)
    }
}
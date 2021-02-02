package kz.kazpost.unloadingarea.ui.auth

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthRepository {

    fun authorizeUser(login: String, password: String): Flow<Response<Boolean>>
    fun getUserLoginAndPassword(): Pair<String?, String?>
    fun exitUser()
}
package kz.kazpost.loadingarea.repositories.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.repositories.auth.models.AuthRequest
import kz.kazpost.loadingarea.ui.auth.AuthRepository
import kz.kazpost.loadingarea.util.extentions.transformBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: UserPreferences
): AuthRepository {

    override fun authorizeUser(login: String, password: String): Flow<Response<Boolean>> {

        return flow { emit(api.makeAuthorization(AuthRequest(login, password))) }.map { response ->
            if (response.isSuccessful) {
                saveUserInfo(login, password, response.body()?.groups?.get(0)?.group)
            }

            response.transformBody { response.isSuccessful }
        }.flowOn(Dispatchers.IO)
    }

    private fun saveUserInfo(login: String, password: String, departmentId: String?) {
        prefs.userLogin = login
        prefs.userPassword = password
        prefs.userDepartmentId = departmentId
    }

    override fun getUserLoginAndPassword(): Pair<String?, String?> {
        return Pair(prefs.userLogin, prefs.userPassword)
    }

    override fun exitUser() {
        prefs.userLogin = null
        prefs.userPassword = null
        prefs.userDepartmentId = null
    }
}
package kz.kazpost.unloadingarea.ui.auth

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kz.kazpost.unloadingarea.base.LoadingViewModel
import kz.kazpost.unloadingarea.util.EventWrapper

class AuthViewModel @ViewModelInject constructor(
    private val repository: AuthRepository
) : LoadingViewModel() {

    private val _authResultLiveData = MediatorLiveData<EventWrapper<Boolean>>()
    val authResultLiveData: LiveData<EventWrapper<Boolean>> = _authResultLiveData

    private var login: String = ""
    private var password: String = ""

    fun init() {
        tryToRestoreAuthorization()
    }

    private fun tryToRestoreAuthorization() {
        val (login, password) = repository.getUserLoginAndPassword()
        if (!login.isNullOrBlank() && !password.isNullOrBlank()) {
            this.login = login
            this.password = password
            authorize()
        }
    }

    fun authorize() {
        val resultLiveData = loadFlow(repository.authorizeUser(login, password))
        _authResultLiveData.addSource(resultLiveData) {
            Log.d(TAG, "authorize: $it")
            _authResultLiveData.postValue(EventWrapper(it == true))
            _authResultLiveData.removeSource(resultLiveData)
        }
    }

    fun setLogin(login: String) {
        this.login = login
    }

    fun setPassword(password: String) {
        this.password = password
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}
package kz.kazpost.loadingarea.ui.transport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.kazpost.loadingarea.base.LoadingViewModel
import kz.kazpost.loadingarea.repositories.TransportRepositoryImpl
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.ui.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class TransportViewModel @Inject constructor(
    private val repository: TransportRepository,
    private val authRepository: AuthRepository
) : LoadingViewModel() {

    val transportFlow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 15)
    ) {
       repository.getTransportPagingSource()
    }.flow
        .cachedIn(viewModelScope)


    fun exitUser() {
        authRepository.exitUser()
    }
}
package kz.kazpost.loadingarea.ui.transport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.kazpost.loadingarea.base.LoadingViewModel
import kz.kazpost.loadingarea.ui._adapters.TransportAdapter
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.ui.auth.AuthRepository
import kz.kazpost.loadingarea.util.EventWrapper
import javax.inject.Inject

@HiltViewModel
class TransportViewModel @Inject constructor(
    private val repository: TransportRepository,
    private val authRepository: AuthRepository
) : LoadingViewModel() {

    private val _actionOnTInvoiceAllowed: MediatorLiveData<EventWrapper<ALLOWED>> =
        MediatorLiveData()

    val actionOnTInvoiceAllowed: LiveData<EventWrapper<ALLOWED>> =
        _actionOnTInvoiceAllowed

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

    fun performTInvoiceAction(
        actionType: TransportAdapter.TransportActionType,
        transportModel: TransportModel
    ) {
        val resultLiveData =
            loadFlow(repository.checkActionPermissionForUser(actionType, transportModel))
        _actionOnTInvoiceAllowed.addSource(resultLiveData) {
            if (it != null) {
                when (it) {
                    is ALLOWED -> {
                        _actionOnTInvoiceAllowed.postValue(EventWrapper(it))
                    }
                    is DENIED -> {
                        showMessageStringResource(it.reasonStringResource)
                    }
                }
            }
        }
    }
}

sealed class ActionPermissionResult
class ALLOWED(
    val actionType: TransportAdapter.TransportActionType,
    val transportModel: TransportModel
) : ActionPermissionResult()

class DENIED(val reasonStringResource: LoadingViewModel.StringResource) : ActionPermissionResult()

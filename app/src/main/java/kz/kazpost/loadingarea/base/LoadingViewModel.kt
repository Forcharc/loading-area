package kz.kazpost.loadingarea.base

import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.util.EventObserver
import kz.kazpost.loadingarea.util.EventWrapper
import kz.kazpost.loadingarea.util.extentions.clickToDismissMode
import kz.kazpost.loadingarea.util.extentions.makeBaseSnackBar
import kz.kazpost.loadingarea.util.extentions.showSnackLong
import retrofit2.Response

open class LoadingViewModel : ViewModel() {
    private val _isLoadingLiveData = MutableLiveData<LoadingStatus>()
    val isLoadingLiveData: LiveData<LoadingStatus> = _isLoadingLiveData

    private val _errorWithRetryActionLiveData =
        MutableLiveData<EventWrapper<ErrorMessageWithRetryAction>>()
    val errorWithRetryActionLiveData: LiveData<EventWrapper<ErrorMessageWithRetryAction>> =
        _errorWithRetryActionLiveData

    private val _errorStringResource = MutableLiveData<EventWrapper<StringResource>>()
    val errorStringResource: LiveData<EventWrapper<StringResource>> = _errorStringResource

    enum class LoadingStatus {
        LOADING(),
        NOT_LOADING()
    }

    data class ErrorMessageWithRetryAction(
        val errorMessage: String,
        val retryAction: (() -> Unit)?
    )

    class StringResource(@StringRes val stringResourceId: Int, vararg val placeholders: Any)

    protected fun showMessageStringResource(stringResource: StringResource) {
        _errorStringResource.postValue(EventWrapper(stringResource))
    }

    protected fun <T> loadFlow(
        flow: Flow<Response<T>>,
        onRetry: (() -> Unit)? = null,
        loadingStatusReceivingLiveData: MutableLiveData<LoadingStatus> = _isLoadingLiveData,
        errorReceivingLiveData: MutableLiveData<EventWrapper<ErrorMessageWithRetryAction>> = _errorWithRetryActionLiveData
    ): LiveData<T?> {
        return flow
            .onStart {
                loadingStatusReceivingLiveData.postValue(LoadingStatus.LOADING)
            }
            .map {
                if (it.isSuccessful) {
                    it.body()
                } else {
                    throw Exception(it.errorBody()?.string()?.removeSurrounding("\""))
                }
            }
            .catch {
                emit(null)
                errorReceivingLiveData.postValue(
                    EventWrapper(
                        ErrorMessageWithRetryAction(
                            it.localizedMessage ?: "Unknown error", onRetry
                        )
                    )
                )
            }
            .onCompletion {
                loadingStatusReceivingLiveData.postValue(LoadingStatus.NOT_LOADING)
            }
            .asLiveData()
    }


    companion object {

        fun <T> MediatorLiveData<T>.observeOnce(observeFrom: LiveData<T?>) {
            addSource(observeFrom) {
                postValue(it)
                removeSource(observeFrom)
            }
        }

        fun Fragment.connectToLoadingViewModel(
            viewModel: LoadingViewModel,
            onLoading: (Boolean) -> Unit = { isLoading ->
                updateProgressIndicator(isLoading)
            }
        ) {
            viewModel.errorWithRetryActionLiveData.observe(viewLifecycleOwner, EventObserver {
                showLoadingErrorSnackBar(it.errorMessage, it.retryAction)
            })
            viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
                onLoading(it == LoadingStatus.LOADING)
            }
            viewModel.errorStringResource.observe(viewLifecycleOwner, EventObserver {
                requireView().showSnackLong(getString(it.stringResourceId, *it.placeholders))
            })
        }

        fun Fragment.showLoadingErrorSnackBar(
            errorMessage: String,
            retryAction: (() -> Unit)?
        ) {
            val snackbar = Snackbar.make(
                requireView(),
                errorMessage,
                Snackbar.LENGTH_LONG
            ).makeBaseSnackBar().clickToDismissMode()


            retryAction?.let { retryAction ->
                snackbar.duration = Snackbar.LENGTH_INDEFINITE
                snackbar.setAction(R.string.retry) { _ ->
                    retryAction()
                }
            }
            snackbar.show()
        }


        fun Fragment.updateProgressIndicator(isLoading: Boolean) {
            // Just random
            val progressIndicatorId = 495391569

            val root = requireView() as ConstraintLayout
            val progressIndicator: LinearProgressIndicator? = root.findViewById(progressIndicatorId)
            if (isLoading) {
                if (requireView() is ConstraintLayout) {
                    if (progressIndicator == null) {
                        createProgressIndicator(progressIndicatorId, root)
                    } else {
                        progressIndicator.isVisible = true
                    }
                }
            } else {
                progressIndicator?.isGone = true
            }
        }

        private fun Fragment.createProgressIndicator(
            loadingViewId: Int,
            root: ConstraintLayout
        ) {
            val progressIndicator = LinearProgressIndicator(requireContext())
            progressIndicator.isIndeterminate = true
            progressIndicator.id = loadingViewId
            root.addView(
                progressIndicator,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val constraints = ConstraintSet()
            constraints.clone(root)
            constraints.connect(
                progressIndicator.id,
                ConstraintSet.TOP,
                root.id,
                ConstraintSet.TOP
            )
            constraints.applyTo(root)
        }
    }
}
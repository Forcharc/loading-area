package kz.kazpost.unloadingarea.base

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import kz.kazpost.unloadingarea.util.EventObserver
import kz.kazpost.unloadingarea.util.EventWrapper
import retrofit2.Response

open class LoadingViewModel : ViewModel() {
    private val _isLoadingLiveData = MutableLiveData<LoadingStatus>()
    val isLoadingLiveData: LiveData<LoadingStatus> = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<EventWrapper<String>>()
    val errorLiveData: LiveData<EventWrapper<String>> = _errorLiveData

    enum class LoadingStatus {
        LOADING(),
        NOT_LOADING()
    }

    protected fun <T> loadFlow(
        flow: Flow<Response<T>>,
        loadingStatusReceivingLiveData: MutableLiveData<LoadingStatus> = _isLoadingLiveData,
        errorReceivingLiveData: MutableLiveData<EventWrapper<String>> = _errorLiveData
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
                errorReceivingLiveData.postValue(EventWrapper(it.localizedMessage))
            }
            .onCompletion {
                loadingStatusReceivingLiveData.postValue(LoadingStatus.NOT_LOADING)
            }
            .asLiveData()
    }


    companion object {
        fun Fragment.connectToLoadingViewModel(
            viewModel: LoadingViewModel,
            onLoading: (Boolean) -> Unit = { isLoading ->
                updateProgressIndicator(isLoading)
            }
        ) {
            viewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            })
            viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
                onLoading(it == LoadingStatus.LOADING)
            }
        }


        private fun Fragment.updateProgressIndicator(isLoading: Boolean) {
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
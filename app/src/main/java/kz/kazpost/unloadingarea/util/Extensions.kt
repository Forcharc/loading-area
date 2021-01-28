package kz.kazpost.unloadingarea.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import kz.kazpost.unloadingarea.base.LoadingViewModel
import okhttp3.ResponseBody
import retrofit2.Response


fun <T, R> Response<T>.transform(func: (T?) -> R): Response<R> {
    return if (this.isSuccessful) {
        Response.success(func(this.body()), this.raw())
    } else {
        Response.error(this.errorBody() ?: ResponseBody.create(null, "Unknown error"), this.raw())
    }
}

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )
}

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
        onLoading(it == LoadingViewModel.LoadingStatus.LOADING)
    }
}

fun View.showSnackShort(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

private fun Fragment.updateProgressIndicator(isLoading: Boolean) {
    // Just random
    val progressIndicatorId: Int = 495391569

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
    root.addView(progressIndicator, MATCH_PARENT, WRAP_CONTENT)
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

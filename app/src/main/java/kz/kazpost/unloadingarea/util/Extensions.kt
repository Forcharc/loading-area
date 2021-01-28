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


fun View.showSnackShort(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

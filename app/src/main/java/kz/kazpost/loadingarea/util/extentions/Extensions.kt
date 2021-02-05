package kz.kazpost.loadingarea.util.extentions

import android.content.Context
import android.util.TypedValue
import okhttp3.ResponseBody
import retrofit2.Response


fun <T, R> Response<T>.transformBody(func: (T?) -> R): Response<R> {
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



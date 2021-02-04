package kz.kazpost.loadingarea.util.extentions

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kz.kazpost.loadingarea.R

fun View.showSnackShort(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).makeBaseSnackBar().show()
}

fun Snackbar.makeBaseSnackBar(): Snackbar {
    val textSize = view.context.resources.getDimension(R.dimen.text_snackbar)
    val snackTextView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    val snackActionButton= view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
    snackTextView.maxLines = 4
    snackTextView.textSize = textSize
    snackActionButton.textSize = textSize
    return this
}

fun Snackbar.clickToDismissMode(): Snackbar {
    view.setOnClickListener {
        dismiss()
    }
    return this
}

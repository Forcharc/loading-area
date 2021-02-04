package kz.kazpost.loadingarea.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemMarginsDecoration(
    private val top: Float,
    private val inBetween: Float,
    private val bottom: Float,
    private val start: Float,
    private val end: Float
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val lastPosition = state.itemCount - 1
        if (lastPosition == 0) {
            outRect.set(start.toInt(), top.toInt(), end.toInt(), bottom.toInt())
        } else {
            when (position) {
                0 -> outRect.set(start.toInt(), top.toInt(), end.toInt(), inBetween.toInt())
                lastPosition -> outRect.set(start.toInt(), 0, end.toInt(), bottom.toInt())
                else -> outRect.set(start.toInt(), 0, end.toInt(), inBetween.toInt())
            }
        }
    }
}
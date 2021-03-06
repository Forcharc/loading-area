package kz.kazpost.loadingarea.ui._models

import androidx.recyclerview.widget.DiffUtil


data class SInvoiceModel(
    val number: String,
    val destination: String,
    val bagCount: Int,
    val parcelCount: Int,
    val id: Long,
    val weight: Int
) {
    class ItemCallback : DiffUtil.ItemCallback<SInvoiceModel>() {
        override fun areItemsTheSame(oldItem: SInvoiceModel, newItem: SInvoiceModel): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: SInvoiceModel, newItem: SInvoiceModel): Boolean {
            return oldItem == newItem
        }

    }
}

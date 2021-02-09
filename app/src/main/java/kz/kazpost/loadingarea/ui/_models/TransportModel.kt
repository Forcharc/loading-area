package kz.kazpost.loadingarea.ui._models

import androidx.recyclerview.widget.DiffUtil

data class TransportModel(
    val id: Int,
    val tInvoiceNumber: String,
    val from: String,
    val to: String,
    val transportType: String,
    // Departments that transport will visit
    val notYetVisitedDepartments: List<String>,
    val index: Int
) {
    class TransportItemCallback : DiffUtil.ItemCallback<TransportModel>() {
        override fun areItemsTheSame(oldItem: TransportModel, newItem: TransportModel): Boolean {
            return oldItem.tInvoiceNumber == newItem.tInvoiceNumber
        }

        override fun areContentsTheSame(oldItem: TransportModel, newItem: TransportModel): Boolean {
            return oldItem == newItem
        }
    }
}

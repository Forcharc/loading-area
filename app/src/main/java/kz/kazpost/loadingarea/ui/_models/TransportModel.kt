package kz.kazpost.loadingarea.ui._models

import androidx.recyclerview.widget.DiffUtil

enum class WorkerState() {
    WRONG_WORKER, RIGHT_WORKER, NO_WORKER
}

data class TransportModel(
    val id: Long,
    val tInvoiceNumber: String,
    val from: String,
    val to: String,
    val transportType: String,
    // Departments that transport will visit
    val notYetVisitedDepartments: List<String>,
    val index: Int,
    val workerState: WorkerState
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

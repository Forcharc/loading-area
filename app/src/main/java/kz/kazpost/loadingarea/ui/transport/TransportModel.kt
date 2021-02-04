package kz.kazpost.loadingarea.ui.transport

data class TransportModel(
    val id: Int,
    val tInvoiceNumber: String,
    val from: String,
    val to: String,
    val transportType: String,
    // Departments that transport will visit
    val notYetVisitedDepartments: List<String>
) {
}

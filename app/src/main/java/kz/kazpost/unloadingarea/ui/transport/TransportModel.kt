package kz.kazpost.unloadingarea.ui.transport

data class TransportModel(
    val tInvoiceNumber: String,
    val from: String,
    val to: String,
    val transportType: String,
    // Departments that transport will visit
    val notYetVisitedDepartments: List<String>
)

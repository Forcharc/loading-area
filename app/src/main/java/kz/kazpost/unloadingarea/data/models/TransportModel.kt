package kz.kazpost.unloadingarea.data.models

data class TransportModel(
    val tInvoiceNumber: String,
    val from: String,
    val to: String,
    val transportType: String
)

package kz.kazpost.loadingarea.ui.s_invoice


data class SInvoiceModel(
    val number: String,
    val destination: String,
    val bagCount: String,
    val parcelCount: String,
    val id: Int
)

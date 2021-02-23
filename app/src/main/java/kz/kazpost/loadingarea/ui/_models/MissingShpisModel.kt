package kz.kazpost.loadingarea.ui._models

data class MissingShpisModel(val missingShpis: List<String>, val tInvoiceNumber: String) {
    fun hasMissingShpis() = missingShpis.isNotEmpty()
}

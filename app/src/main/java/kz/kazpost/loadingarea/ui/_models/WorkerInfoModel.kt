package kz.kazpost.loadingarea.ui._models

data class WorkerInfoModel(
    val workerLogin: String?,
    val transportDepartment: String?,
    val status: String?,
    val workerCurrentTInvoiceNumbers: List<String>
)

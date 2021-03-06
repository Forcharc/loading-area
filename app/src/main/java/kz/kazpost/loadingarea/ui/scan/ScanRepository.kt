package kz.kazpost.loadingarea.ui.scan

import kotlinx.coroutines.flow.Flow
import kz.kazpost.loadingarea.ui._models.MissingShpisModel
import kz.kazpost.loadingarea.ui._models.ParcelCategoryModel
import retrofit2.Response

interface ScanRepository {
    fun loadTInvoiceInfo(
        index: Int,
        tInvoiceNumber: String
    ): Flow<Response<List<ParcelCategoryModel>>>

    fun rememberAddedParcel(shpi: String, tInvoiceNumber: String)
    fun verifyThatAllParcelsAreIncluded(
        factParcels: List<String>,
        tInvoiceId: Long,
        index: Int,
        tInvoiceNumber: String
    ): Flow<Response<MissingShpisModel>>

    fun decoupleParcelsFromTInvoice(
        missingParcels: List<String>,
        tInvoiceNumber: String
    ): Flow<Response<Boolean>>

    fun setWorkerForTransport(tInvoiceNumber: String): Flow<Response<Boolean>>
}
package kz.kazpost.loadingarea.ui.scan

import kotlinx.coroutines.flow.Flow
import kz.kazpost.loadingarea.ui._models.ParcelCategoryModel
import retrofit2.Response

interface ScanRepository {
    fun loadTInvoiceInfo(
        index: Int,
        tInvoiceNumber: String
    ): Flow<Response<List<ParcelCategoryModel>>>

    fun rememberAddedParcel(shpi: String, tInvoiceNumber: String)
    fun verifyThatAllParcelsAreIncluded(factParcels: List<String>, tInvoiceId: Int, index: Int): Flow<Response<Boolean>>
}
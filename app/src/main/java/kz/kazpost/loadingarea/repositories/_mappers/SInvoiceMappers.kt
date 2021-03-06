package kz.kazpost.loadingarea.repositories._mappers

import kz.kazpost.loadingarea.api._responses.SInvoiceResponse
import kz.kazpost.loadingarea.ui._models.SInvoiceModel
import kz.kazpost.loadingarea.util.StringConstants

object SInvoiceMappers {

    fun SInvoiceResponse.toSInvoiceModel(): SInvoiceModel {
        return SInvoiceModel(
            this.destinationListId ?: StringConstants.stringUnknown,
            this.toDepartment?.longNameRu ?: StringConstants.stringUnknown,
            (this.totalNumberOfLabels ?: 0),
            this.totalNumberOfPLs ?: 0,
            this.id ?: -1,
            this.totalWeight ?: 0
        )
    }
}
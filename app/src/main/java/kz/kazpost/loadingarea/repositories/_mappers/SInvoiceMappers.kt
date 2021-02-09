package kz.kazpost.loadingarea.repositories._mappers

import kz.kazpost.loadingarea.api._responses.SInvoiceResponse
import kz.kazpost.loadingarea.ui._models.SInvoiceModel
import kz.kazpost.loadingarea.util.StringConstants

object SInvoiceMappers {

    fun SInvoiceResponse.toSInvoiceModel(): SInvoiceModel {
        return SInvoiceModel(
            this.destinationListId ?: StringConstants.stringUnknown,
            this.toDepartment?.longNameRu ?: StringConstants.stringUnknown,
            this.totalNumberOfLabels?.toString() ?: StringConstants.stringUnknown,
            this.totalNumberMails?.toString() ?: StringConstants.stringUnknown,
            this.id ?: -1
        )
    }
}
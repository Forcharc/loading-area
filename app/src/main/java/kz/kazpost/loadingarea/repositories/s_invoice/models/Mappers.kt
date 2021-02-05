package kz.kazpost.loadingarea.repositories.s_invoice.models

import kz.kazpost.loadingarea.repositories.s_invoice.SInvoiceDBModel
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceModel
import kz.kazpost.loadingarea.util.StringConstants

object Mappers {

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
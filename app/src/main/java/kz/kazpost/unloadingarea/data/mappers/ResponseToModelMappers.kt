package kz.kazpost.unloadingarea.data.mappers

import kz.kazpost.unloadingarea.data.models.TransportModel
import kz.kazpost.unloadingarea.data.responses.TransportListResponse
import kz.kazpost.unloadingarea.util.StringConstants

object ResponseToModelMappers {
    fun transportListResponseToTransportModelList(response: TransportListResponse): List<TransportModel> {
        return response.transportList.map {
            TransportModel(
                it.transportListId ?: StringConstants.stringUnknown,
                it.fromDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.toDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.flightDTO?.transportType ?: StringConstants.stringUnknown
            )
        }
    }
}
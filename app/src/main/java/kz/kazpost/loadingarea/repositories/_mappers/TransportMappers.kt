package kz.kazpost.loadingarea.repositories._mappers

import kz.kazpost.loadingarea.api._responses.ItineraryDTOEntryResponse
import kz.kazpost.loadingarea.api._responses.TransportListResponse
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.util.StringConstants

object TransportMappers {
    fun transportListResponseToTransportModelList(
        currentDepartment: String,
        response: TransportListResponse
    ): List<TransportModel> {
        return response.transportList.map {
            TransportModel(
                it.id ?: -1,
                it.transportListId ?: StringConstants.stringUnknown,
                it.fromDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.toDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.flightDTO?.transportType ?: StringConstants.stringUnknown,
                getNotYetVisitedDepartments(it.flightDTO?.itineraryDTO?.entries, currentDepartment),
                it.currentIndex ?: -1
            )
        }
    }

    private fun getNotYetVisitedDepartments(
        entries: List<ItineraryDTOEntryResponse?>?,
        currentDepartment: String
    ): List<String> {
        if (entries == null) {
            return emptyList()
        } else {
            var isAfterCurrentDepartment = false
            val notYetVisitedDepartments: MutableList<String> = mutableListOf()
            entries.forEach { entry ->
                if (isAfterCurrentDepartment) {
                    entry?.dept?.name?.let {
                        notYetVisitedDepartments.add(it)
                    }
                } else if (entry?.dept?.name == currentDepartment) {
                    isAfterCurrentDepartment = true
                }
            }
            return notYetVisitedDepartments
        }
    }
}
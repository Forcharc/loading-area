package kz.kazpost.unloadingarea.repositories.transport.models

import kz.kazpost.unloadingarea.ui.transport.TransportModel
import kz.kazpost.unloadingarea.util.StringConstants

object Mappers {
    fun transportListResponseToTransportModelList(
        currentDepartment: String,
        response: TransportListResponse
    ): List<TransportModel> {
        return response.transportList.map {
            TransportModel(
                it.transportListId ?: StringConstants.stringUnknown,
                it.fromDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.toDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.flightDTO?.transportType ?: StringConstants.stringUnknown,
                getNotYetVisitedDepartments(it.flightDTO?.itineraryDTO?.entries, currentDepartment)
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
package kz.kazpost.loadingarea.repositories._mappers

import kz.kazpost.loadingarea.api._responses.ItineraryDTOEntryResponse
import kz.kazpost.loadingarea.api._responses.TransportResponse
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.ui._models.WorkerState
import kz.kazpost.loadingarea.util.StringConstants

object TransportMappers {
    fun transportListResponseToTransportModelList(
        userLogin: String,
        currentDepartment: String,
        transportList: List<TransportResponse>
    ): List<TransportModel> {
        return transportList.map {
            TransportModel(
                it.id ?: -1,
                it.transportListId ?: StringConstants.stringUnknown,
                it.fromDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.toDepartment?.longNameRu ?: StringConstants.stringUnknown,
                it.flightDTO?.transportType ?: StringConstants.stringUnknown,
                getNotYetVisitedDepartments(it.flightDTO?.itineraryDTO?.entries, currentDepartment),
                it.currentIndex ?: -1,
                if (it.worker.isNullOrBlank()) WorkerState.NO_WORKER else if (it.worker == userLogin) WorkerState.RIGHT_WORKER else WorkerState.WRONG_WORKER
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
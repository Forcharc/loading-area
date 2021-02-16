package kz.kazpost.loadingarea.ui.transport

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow
import kz.kazpost.loadingarea.repositories.TransportRepositoryImpl
import kz.kazpost.loadingarea.repositories._mappers.TransportMappers
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.util.DateUtils
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

interface TransportRepository {
    fun getTransportPagingSource(): TransportPagingSource

    abstract class TransportPagingSource : PagingSource<Int, TransportModel>()
}
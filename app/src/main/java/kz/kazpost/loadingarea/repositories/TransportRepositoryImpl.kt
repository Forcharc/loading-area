package kz.kazpost.loadingarea.repositories

import androidx.paging.PagingState
import kz.kazpost.loadingarea.api.TransportApi
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.repositories._mappers.TransportMappers
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.ui.transport.TransportRepository
import kz.kazpost.loadingarea.util.DateUtils
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TransportRepositoryImpl @Inject constructor(
    private val api: TransportApi,
    private val prefs: UserPreferences
) : TransportRepository {


    override fun getTransportPagingSource(): TransportRepository.TransportPagingSource {
        return TransportPagingSource()
    }

    inner class TransportPagingSource : TransportRepository.TransportPagingSource() {
        private val userDepartmentId = prefs.userDepartmentId ?: "unknown user department"
        private val yesterdayDate = DateUtils.getYesterdayDate()

        override fun getRefreshKey(state: PagingState<Int, TransportModel>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransportModel> {
            try {
                // Start refresh at page 1 if undefined.
                val nextPageNumber = params.key ?: 1
                val response = api.getTransportList(
                    userDepartmentId,
                    yesterdayDate,
                    nextPageNumber
                )
                val transportModelList = TransportMappers.transportListResponseToTransportModelList(
                    userDepartmentId,
                    response.body()?.transportList ?: emptyList()
                )

                return LoadResult.Page(
                    data = transportModelList,
                    prevKey = if (nextPageNumber >= 2) nextPageNumber - 1 else null, // Only paging forward.
                    nextKey = if (response.body()?.transportList?.size != 0) (nextPageNumber + 1) else null
                )
            } catch (e: IOException) {
                // IOException for network failures.
                return LoadResult.Error(e)
            } catch (e: HttpException) {
                // HttpException for any non-2xx HTTP status codes.
                return LoadResult.Error(e)
            }
        }

    }
}
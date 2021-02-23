package kz.kazpost.loadingarea.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kz.kazpost.loadingarea.api.ScanApi
import kz.kazpost.loadingarea.api._requests.VerifyThatAllParcelsIncludedRequest
import kz.kazpost.loadingarea.api._responses.LabelItemResponse
import kz.kazpost.loadingarea.api._responses.MailResponse
import kz.kazpost.loadingarea.database.AddedShpisDao
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.database._db_models.AddedShpisDBModel
import kz.kazpost.loadingarea.ui._models.MissingShpisModel
import kz.kazpost.loadingarea.ui._models.ParcelCategoryModel
import kz.kazpost.loadingarea.ui.scan.ScanRepository
import kz.kazpost.loadingarea.util.StringConstants
import kz.kazpost.loadingarea.util.extentions.transformBody
import retrofit2.Response
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val api: ScanApi,
    private val addedShpisDao: AddedShpisDao,
    private val prefs: UserPreferences
) : ScanRepository {
    override fun loadTInvoiceInfo(
        index: Int,
        tInvoiceNumber: String
    ): Flow<Response<List<ParcelCategoryModel>>> {
        return flow {
            emit(
                api.getTInvoiceInfo(tInvoiceNumber, index)
            )
        }.map { response ->
            response.transformBody { info ->
                val result = HashMap<String, ParcelCategoryModel>()
                if (info?.labelItems != null) {
                    putCategoriesFromLabelsIntoResult(info.labelItems, result)
                }
                if (info?.mailItems != null) {
                    putCategoriesForMailsIntoResult(info.mailItems, result)
                }
                var rememberedShpis = emptyList<AddedShpisDBModel>()
                runBlocking {
                    rememberedShpis = addedShpisDao.getRememberedShpisForTInvoice(tInvoiceNumber)
                }
                putRememberedShpisIntoResult(rememberedShpis, result)
                result.values.toList()
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun putRememberedShpisIntoResult(
        rememberedShpis: List<AddedShpisDBModel>,
        result: java.util.HashMap<String, ParcelCategoryModel>
    ) {
        rememberedShpis.forEach { rememberedShpi ->
            val shpiCategory =
                result.values.find { it.planShpis.find { rememberedShpi.shpi == it } != null }
            shpiCategory?.factShpis =
                shpiCategory?.factShpis?.toMutableList()?.apply { add(rememberedShpi.shpi) }
                    ?: emptyList()
        }
    }

    private fun putCategoriesForMailsIntoResult(
        mailItems: List<MailResponse?>,
        result: HashMap<String, ParcelCategoryModel>
    ) {
        mailItems.forEach {
            it?.let {
                val type = it.mailType ?: StringConstants.stringUnknown
                val name = it.name ?: StringConstants.stringUnknown
                val shpi = it.mailId ?: it.packetId ?: StringConstants.stringUnknown
                val value = result[type]
                if (value == null) {
                    result[type] = ParcelCategoryModel(name, mutableListOf(shpi), mutableListOf())
                } else {
                    value.planShpis.add(shpi)
                }
            }
        }
    }

    private fun putCategoriesFromLabelsIntoResult(
        labelItems: List<LabelItemResponse?>,
        result: HashMap<String, ParcelCategoryModel>
    ) {
        labelItems.forEach {
            it?.let {
                val type = it.labelType ?: StringConstants.stringUnknown
                val name = it.name ?: StringConstants.stringUnknown
                val shpi = it.labelListId ?: StringConstants.stringUnknown
                val value = result[type]
                if (value == null) {
                    result[type] = ParcelCategoryModel(name, mutableListOf(shpi), mutableListOf())
                } else {
                    value.planShpis.add(shpi)
                }
            }
        }
    }

    override fun rememberAddedParcel(shpi: String, tInvoiceNumber: String) {
        CoroutineScope(Dispatchers.IO).launch {
            addedShpisDao.rememberAddedShpi(AddedShpisDBModel(shpi, tInvoiceNumber))
        }
    }

    override fun verifyThatAllParcelsAreIncluded(
        factParcels: List<String>,
        tInvoiceId: Int,
        index: Int,
        tInvoiceNumber: String
    ): Flow<Response<MissingShpisModel>> {
        val request =
            VerifyThatAllParcelsIncludedRequest(
                factParcels,
                tInvoiceId,
                prefs.userDepartmentId!!,
                index
            )
        return flow {
            emit(api.verifyThatAllParcelsIncluded(request))
        }.map { response ->
            response.transformBody {
                if (it?.isSuccessful() == true) {
                    CoroutineScope(Dispatchers.IO).launch {
/*
                        addedShpisDao.deleteRememberedShpisForTInvoice(tInvoiceNumber)
*/
                    }
                }
                if (it?.isSuccessful() == true) MissingShpisModel(emptyList(), tInvoiceNumber)
                else MissingShpisModel(it?.missingShpis?.filterNotNull() ?: emptyList(), tInvoiceNumber)
            }
        }.flowOn(Dispatchers.IO)
    }
}
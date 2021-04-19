package kz.kazpost.loadingarea.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.base.LoadingViewModel
import kz.kazpost.loadingarea.ui._models.MissingShpisModel
import kz.kazpost.loadingarea.ui._models.ParcelCategoryModel
import kz.kazpost.loadingarea.util.EventWrapper
import kz.kazpost.loadingarea.util.ShpiUtil.isBInvoice
import kz.kazpost.loadingarea.util.ShpiUtil.isLabel
import kz.kazpost.loadingarea.util.ShpiUtil.isMail
import kz.kazpost.loadingarea.util.ShpiUtil.isMjd
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ScanViewModel @Inject constructor(private val repository: ScanRepository) :
    LoadingViewModel() {

    private var tInvoiceId by Delegates.notNull<Int>()
    private var index by Delegates.notNull<Int>()
    private lateinit var tInvoiceNumber: String

    private val _categoriesLiveData = MediatorLiveData<List<ParcelCategoryModel>>()
    val categoriesLiveData: LiveData<List<ParcelCategoryModel>> = _categoriesLiveData

    private val _scanSuccessLiveData = MediatorLiveData<Boolean>()
    val scanSuccessLiveData: LiveData<Boolean> = _scanSuccessLiveData

    private val _decoupleSuccessLiveData = MediatorLiveData<Boolean>()
    val decoupleSuccessLiveData: LiveData<Boolean> = _decoupleSuccessLiveData

    private val _clearShpiLiveData = MutableLiveData<EventWrapper<Boolean>>()
    val clearShpiLiveData: LiveData<EventWrapper<Boolean>> = _clearShpiLiveData

    private val _missingShpisLiveData = MutableLiveData<EventWrapper<MissingShpisModel>>()
    val missingShpisLiveData: LiveData<EventWrapper<MissingShpisModel>> = _missingShpisLiveData

    fun init(index: Int, tInvoiceNumber: String, tInvoiceId: Int) {
        this.index = index
        this.tInvoiceNumber = tInvoiceNumber
        this.tInvoiceId = tInvoiceId
    }

    fun loadTInvoiceInfo() {
        val result = loadFlow(
            repository.loadTInvoiceInfo(index, tInvoiceNumber),
            onRetry = this::loadTInvoiceInfo
        )

        _categoriesLiveData.observeOnce(result)

    }

    fun onShpiChanged(shpi: String) {
        val upperCaseShpi = shpi.toUpperCase(Locale.getDefault())
        if (isShpiCorrect(upperCaseShpi)) {
            if (isShpiPresentInCategories(upperCaseShpi)) {
                if (isShpiNotAdded(upperCaseShpi)) {
                    addShpiToCategories(upperCaseShpi)
                } else {
                    showMessageStringResource(R.string.shpi_already_added)
                    _clearShpiLiveData.value = EventWrapper(true)
                }
            } else {
                showMessageStringResource(R.string.wrong_parcel)
            }
        }
    }

    private fun addShpiToCategories(shpi: String) {
        val categories = categoriesLiveData.value
        if (categories != null) {
            val shpiCategoryIndex =
                categories.indexOfFirst { category -> category.planShpis.find { it == shpi } != null }
            if (shpiCategoryIndex != -1) {
                val newCategories =
                    createCopyOfCategoriesWithAddedShpis(categories, shpiCategoryIndex, shpi)
                repository.rememberAddedParcel(shpi, tInvoiceNumber)
                _categoriesLiveData.value = newCategories
                _clearShpiLiveData.value = EventWrapper(true)
                showMessageStringResource(R.string.successfully_added)
            } else {
                showMessageStringResource(R.string.error_try_again)
            }
        } else {
            showMessageStringResource(R.string.error_try_again)
        }
    }

    // So that adapter won't have same link to list so it can differentiate items
    private fun createCopyOfCategoriesWithAddedShpis(
        categories: List<ParcelCategoryModel>,
        shpiCategoryIndex: Int,
        shpi: String
    ): List<ParcelCategoryModel> {
        val shpiCategory = categories[shpiCategoryIndex]
        val newCategories = categories.toMutableList()
        val newFactShpis = shpiCategory.factShpis.toMutableList()
        newFactShpis.add(shpi)
        newCategories[shpiCategoryIndex] = shpiCategory.copy(factShpis = newFactShpis)
        return newCategories
    }

    private fun isShpiNotAdded(shpi: String): Boolean {
        val categories = categoriesLiveData.value
        return if (categories != null) {
            categories.find { category -> category.factShpis.find { it == shpi } != null } == null
        } else {
            false
        }
    }

    private fun isShpiPresentInCategories(shpi: String): Boolean {
        val categories = categoriesLiveData.value
        return if (categories != null) {
            categories.find { category -> category.planShpis.find { it == shpi } != null } != null
        } else {
            false
        }
    }

    private fun isShpiCorrect(shpi: String): Boolean {
        return isLabel(shpi) || isMail(shpi) || isBInvoice(shpi) || isMjd(shpi)
    }

    fun confirmParcels() {
        val factParcels = getFactParcelShpis()
        val result = loadFlow(
            repository.verifyThatAllParcelsAreIncluded(
                factParcels,
                tInvoiceId,
                index,
                tInvoiceNumber
            )
        )
        _scanSuccessLiveData.addSource(result) {
            it?.let {
                if (!it.hasMissingShpis()) {
                    showMessageStringResource(R.string.scan_success)
                    _scanSuccessLiveData.postValue(true)
                } else {
                    _missingShpisLiveData.postValue(
                        EventWrapper(it)
                    )
                }
            }
            _scanSuccessLiveData.removeSource(result)
        }
    }

    private fun getFactParcelShpis(): List<String> {
        val factParcelShpis = mutableListOf<String>()
        categoriesLiveData.value?.forEach { factParcelShpis.addAll(it.factShpis) }
        return factParcelShpis
    }

    fun decoupleMissingParcelsFromTInvoice() {
        val result = loadFlow(
            repository.decoupleParcelsFromTInvoice(
                missingShpisLiveData.value?.peek()?.missingShpis ?: emptyList(),
                tInvoiceNumber
            )
        )
        _decoupleSuccessLiveData.addSource(result) {
            it?.let {
                if (it) {
                    showMessageStringResource(R.string.decouple_scan_success)
                    _decoupleSuccessLiveData.postValue(true)
                    loadTInvoiceInfo()
                }
            }
            _scanSuccessLiveData.removeSource(result)
        }


    }

}
package kz.kazpost.loadingarea.database

import androidx.room.*
import kz.kazpost.loadingarea.database._db_models.AddedShpisDBModel

@Dao
interface AddedShpisDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun rememberAddedShpi(sInvoice: AddedShpisDBModel)

    @Query("DELETE FROM added_shpis WHERE t_invoice_number = :tInvoiceNumber")
    suspend fun deleteRememberedShpisForTInvoice(tInvoiceNumber: String)

    @Query("SELECT * FROM added_shpis WHERE t_invoice_number = :tInvoiceNumber")
    suspend fun getRememberedShpisForTInvoice(tInvoiceNumber: String): List<AddedShpisDBModel>
}
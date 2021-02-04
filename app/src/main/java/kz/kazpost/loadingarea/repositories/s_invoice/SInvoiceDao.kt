package kz.kazpost.loadingarea.repositories.s_invoice

import androidx.room.*

@Dao
interface SInvoiceDao {
    @Query("SELECT * FROM s_invoice WHERE t_invoice_number = :tInvoiceNumber")
    suspend fun getThoseInTInvoice(tInvoiceNumber: String): List<SInvoiceDBModel>

    @Query("DELETE FROM s_invoice WHERE number IN (:numbers)")
    suspend fun deleteByNumbers(numbers: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSInvoice(sInvoice: SInvoiceDBModel)

    @Query("SELECT * FROM s_invoice WHERE number = :number")
    suspend fun getSInvoiceByNumber(number: String): SInvoiceDBModel?
}
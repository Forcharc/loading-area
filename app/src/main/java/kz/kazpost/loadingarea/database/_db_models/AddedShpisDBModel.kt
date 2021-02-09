package kz.kazpost.loadingarea.database._db_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "added_shpis")
data class AddedShpisDBModel(
    @PrimaryKey @ColumnInfo(name = "shpi")val shpi: String,
    @ColumnInfo(name = "t_invoice_number") val tInvoiceNumber: String
)

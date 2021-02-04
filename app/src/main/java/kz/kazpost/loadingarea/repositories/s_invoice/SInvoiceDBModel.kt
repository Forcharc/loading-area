package kz.kazpost.loadingarea.repositories.s_invoice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "s_invoice")
data class SInvoiceDBModel(
    @PrimaryKey @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "destination") val destination: String,
    @ColumnInfo(name = "bag_count") val bagCount: String,
    @ColumnInfo(name = "parcel_count") val parcelCount: String,
    @ColumnInfo(name = "t_invoice_number") val tInvoiceNumber: String
)

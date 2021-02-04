package kz.kazpost.loadingarea.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.kazpost.loadingarea.repositories.s_invoice.SInvoiceDBModel
import kz.kazpost.loadingarea.repositories.s_invoice.SInvoiceDao

@Database(entities = [SInvoiceDBModel::class], version = 1)
abstract class RoomDatabase: RoomDatabase() {

    abstract fun sInvoiceDao(): SInvoiceDao
}
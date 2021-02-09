package kz.kazpost.loadingarea.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.kazpost.loadingarea.database._db_models.AddedShpisDBModel

@Database(entities = [AddedShpisDBModel::class], version = 1)
abstract class RoomDatabase: RoomDatabase() {

    abstract fun addedShpisDao(): AddedShpisDao
}
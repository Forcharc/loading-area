package kz.kazpost.loadingarea.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.kazpost.loadingarea.api.ScanApi
import kz.kazpost.loadingarea.database.AddedShpisDao
import kz.kazpost.loadingarea.database.RoomDatabase
import kz.kazpost.loadingarea.repositories.ScanRepositoryImpl
import kz.kazpost.loadingarea.ui.scan.ScanRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class ScanModule {
    @Binds
    abstract fun bindScanRepository(impl: ScanRepositoryImpl): ScanRepository

    companion object {
        @Provides
        fun provideTransportApi(retrofit: Retrofit): ScanApi {
            return retrofit.create(ScanApi::class.java)
        }

        @Provides
        fun provideAddedShpisDao(room: RoomDatabase): AddedShpisDao {
            return room.addedShpisDao()
        }
    }
}
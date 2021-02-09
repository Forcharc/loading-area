package kz.kazpost.loadingarea.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.kazpost.loadingarea.api.TransportApi
import kz.kazpost.loadingarea.repositories.TransportRepositoryImpl
import kz.kazpost.loadingarea.ui.transport.TransportRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class TransportModule {
    @Binds
    abstract fun bindTransportRepository(impl: TransportRepositoryImpl): TransportRepository

    companion object {
        @Provides
        fun provideTransportApi(retrofit: Retrofit): TransportApi {
            return retrofit.create(TransportApi::class.java)
        }
    }
}
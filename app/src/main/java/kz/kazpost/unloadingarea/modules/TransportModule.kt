package kz.kazpost.unloadingarea.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import kz.kazpost.unloadingarea.repositories.add_s_invoice.AddSInvoiceRepositoryImpl
import kz.kazpost.unloadingarea.repositories.add_s_invoice.SInvoiceApi
import kz.kazpost.unloadingarea.repositories.auth.AuthApi
import kz.kazpost.unloadingarea.repositories.auth.AuthRepositoryImpl
import kz.kazpost.unloadingarea.repositories.transport.TransportApi
import kz.kazpost.unloadingarea.repositories.transport.TransportRepositoryImpl
import kz.kazpost.unloadingarea.ui.add_s_invoice.AddSInvoiceRepository
import kz.kazpost.unloadingarea.ui.auth.AuthRepository
import kz.kazpost.unloadingarea.ui.transport.TransportRepository
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
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
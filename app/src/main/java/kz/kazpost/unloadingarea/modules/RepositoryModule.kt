package kz.kazpost.unloadingarea.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kz.kazpost.unloadingarea.repositories.AddSInvoiceRepositoryImpl
import kz.kazpost.unloadingarea.repositories.AuthRepositoryImpl
import kz.kazpost.unloadingarea.repositories.TransportRepositoryImpl
import kz.kazpost.unloadingarea.ui.add_s_invoice.AddSInvoiceRepository
import kz.kazpost.unloadingarea.ui.auth.AuthRepository
import kz.kazpost.unloadingarea.ui.transport.TransportRepository

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindTransportRepository(impl: TransportRepositoryImpl): TransportRepository

    @Binds
    fun bindAddSInvoiceRepository(impl: AddSInvoiceRepositoryImpl): AddSInvoiceRepository
}
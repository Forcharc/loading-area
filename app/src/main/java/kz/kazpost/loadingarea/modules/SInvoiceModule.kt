package kz.kazpost.loadingarea.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.kazpost.loadingarea.api.SInvoiceApi
import kz.kazpost.loadingarea.repositories.SInvoiceRepositoryImpl
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class SInvoiceModule {
    @Binds
    abstract fun bindAddSInvoiceRepository(impl: SInvoiceRepositoryImpl): SInvoiceRepository

    companion object {
        @Provides
        fun provideSInvoiceApi(retrofit: Retrofit): SInvoiceApi {
            return retrofit.create(SInvoiceApi::class.java)
        }

    }
}
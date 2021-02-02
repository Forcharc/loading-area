package kz.kazpost.unloadingarea.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import kz.kazpost.unloadingarea.repositories.add_s_invoice.AddSInvoiceRepositoryImpl
import kz.kazpost.unloadingarea.repositories.add_s_invoice.SInvoiceApi
import kz.kazpost.unloadingarea.ui.add_s_invoice.AddSInvoiceRepository
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
abstract class AddSInvoiceModule {
    @Binds
    abstract fun bindAddSInvoiceRepository(impl: AddSInvoiceRepositoryImpl): AddSInvoiceRepository

    companion object {
        @Provides
        fun provideSInvoiceApi(retrofit: Retrofit): SInvoiceApi {
            return retrofit.create(SInvoiceApi::class.java)
        }
    }
}
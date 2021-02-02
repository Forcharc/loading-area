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
import kz.kazpost.unloadingarea.ui.add_s_invoice.AddSInvoiceRepository
import kz.kazpost.unloadingarea.ui.auth.AuthRepository
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides
        fun provideAuthApi(retrofit: Retrofit): AuthApi {
            return retrofit.create(AuthApi::class.java)
        }
    }
}
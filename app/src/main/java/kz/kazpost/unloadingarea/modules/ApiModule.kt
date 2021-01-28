package kz.kazpost.unloadingarea.modules

import android.annotation.SuppressLint
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kz.kazpost.unloadingarea.BuildConfig
import kz.kazpost.unloadingarea.api.AuthApi
import kz.kazpost.unloadingarea.api.TransportApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.jvm.Throws

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideTransportApi(retrofit: Retrofit): TransportApi {
        return retrofit.create(TransportApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideDefaultOkHttpClient(): OkHttpClient = try {
        val okHttpBuilder = getAllTrustingOkHttpBuilder()

        val interceptor = HttpLoggingInterceptor { message -> Log.d("okHttp3: ", message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpBuilder
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    } catch (e: Exception) {
        throw RuntimeException(e)
    }

    // Trusts all certificates (POST.kz api has issues with that)
    private fun getAllTrustingOkHttpBuilder(): OkHttpClient.Builder {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(
            sslSocketFactory,
            trustAllCerts[0] as X509TrustManager
        )
        builder.hostnameVerifier { _, _ -> true }
        return builder
    }
}
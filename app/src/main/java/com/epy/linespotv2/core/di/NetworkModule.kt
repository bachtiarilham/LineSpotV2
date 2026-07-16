package com.epy.linespotv2.core.di

import com.epy.linespotv2.core.network.TokenRefreshAuthenticator
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

//module sebagai entry point dagger. tujuan untuk mengetahui dependency menggunakannya dari sini
@Module
//intallin untuk scope dan lifetime (bisa di substitusi menjadi (ActivityComponent :: class) )
@InstallIn(SingletonComponent :: class)
object NetworkModule {

//    private const val BASE_URL = "http://localhost/:8080/"

    private const val BASE_URL = "https://nonstatutory-wonda-preadherently.ngrok-free.dev"
    //jika menggunakan fungsi singleton, perlu provide sehingga hilt yang memikirkannya
    @Provides
    //jika butuh fungsi yang hanya sekali bikin
    @Singleton
    fun provideLoggingInterceptor () : HttpLoggingInterceptor = HttpLoggingInterceptor().apply{
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor (prefs : AppPreferences) : Interceptor =
        Interceptor { chain ->
            // buat header accept yang berisi application/json
            val requestBuilder = chain.request()
                .newBuilder()
                .header("Accept","application/json")

            if (
                prefs.accessToken.isNotBlank() &&
                !chain.request().url.encodedPath.contains("/api/v2/linespot/auth/login") &&
                !chain.request().url.encodedPath.contains("/api/v2/linespot/auth/refresh")
            ) {
                requestBuilder.header("Authorization", "Bearer ${prefs.accessToken}")
            }
            chain.proceed(requestBuilder.build())
        }

    @Provides
    @Singleton
    @Named ("plain_client")
    fun providePlainOkHttpClient (
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(10,TimeUnit.SECONDS)
        .readTimeout(15,TimeUnit.SECONDS)
        .writeTimeout(15,TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @Named ("refresh_retrofit")
    fun provideRefreshRetrofit (
        @Named("plain_client") okHttpClient: OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory (GsonConverterFactory.create())
        .build()

// yang dipake
    @Provides
    @Singleton
    @Named("refresh_api_service")
    fun provideRefreshApiService(
        @Named ("refresh_retrofit") retrofit: Retrofit
    ) : ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
        tokenRefreshAuthenticator: TokenRefreshAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .authenticator(tokenRefreshAuthenticator)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//yangdipake
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
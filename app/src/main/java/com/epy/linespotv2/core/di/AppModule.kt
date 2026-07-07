package com.epy.linespotv2.core.di

import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.data.repository_impl.home.HomeRepositoryImpl
import com.epy.linespotv2.data.repository_impl.auth.AuthRepositoryImpl
import com.epy.linespotv2.data.repository_impl.helper.GetLokasiRepositoryImpl
import com.epy.linespotv2.data.repository_impl.payment.PostParkingRepositoryImpl
import com.epy.linespotv2.data.repository_impl.laporan.LaporanRepositoryImpl
import com.epy.linespotv2.data.repository_impl.payment.GetPembayaranStatusRepositoryImpl
import com.epy.linespotv2.data.repository_impl.riwayat.RiwayatRepositoryImpl
import com.epy.linespotv2.data.repository_impl.payment.PostPaymentParkingRepositoryImpl
import com.epy.linespotv2.data.repository_impl.subscription.SubscribeRepositoryImpl
import com.epy.linespotv2.domain.repository.auth.AuthRepository
import com.epy.linespotv2.domain.repository.home.HomeRepository
import com.epy.linespotv2.domain.repository.helper.GetLokasiRepository
import com.epy.linespotv2.domain.repository.payment.PostParkingRepository
import com.epy.linespotv2.domain.repository.laporan.LaporanRepository
import com.epy.linespotv2.domain.repository.payment.GetPembayaranStatusRepository
import com.epy.linespotv2.domain.repository.payment.PostPaymentParkingRepository
import com.epy.linespotv2.domain.repository.subcription.SubscribeRepository
import com.epy.linespotv2.domain.repository.riwayat.RiwayatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
    @Binds @Singleton
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
    @Binds @Singleton
    abstract fun bindScanRepository(impl: PostPaymentParkingRepositoryImpl): PostPaymentParkingRepository
    @Binds @Singleton
    abstract fun bindSubscribeRepository(impl: SubscribeRepositoryImpl): SubscribeRepository
    @Binds @Singleton
    abstract fun bindRiwayatRepository(impl: RiwayatRepositoryImpl): RiwayatRepository
    @Binds @Singleton
    abstract fun bindGetLokasiRepository(impl: GetLokasiRepositoryImpl): GetLokasiRepository
    @Binds @Singleton
    abstract fun bindLaporanRepository(impl: LaporanRepositoryImpl): LaporanRepository
    @Binds @Singleton
    abstract fun bindInputManualRepository(impl: PostParkingRepositoryImpl): PostParkingRepository
    @Binds @Singleton
    abstract fun bindPembayaranStatusRepository(impl: GetPembayaranStatusRepositoryImpl): GetPembayaranStatusRepository

    companion object {
        @Provides
        @Singleton
        fun provideDispatcher(): Dispatcher = object : Dispatcher {
            override val io      = Dispatchers.IO      // kotlinx.coroutines.Dispatchers
            override val main    = Dispatchers.Main
            override val default = Dispatchers.Default
        }
    }
}

package com.epy.linespotv2.core.di

import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.data.repository_impl.auth.AuthRepositoryImpl
import com.epy.linespotv2.data.repository_impl.helper.GetLokasiRepositoryImpl
import com.epy.linespotv2.data.repository_impl.helper.GetTarifRepositoryImpl
import com.epy.linespotv2.data.repository_impl.helper.GetTopUpRepositoryImpl
import com.epy.linespotv2.data.repository_impl.home.CustomerHomeRepositoryImpl
import com.epy.linespotv2.data.repository_impl.home.JukirHomeRepositoryImpl
import com.epy.linespotv2.data.repository_impl.laporan.LaporanRepositoryImpl
import com.epy.linespotv2.data.repository_impl.payment.GetPembayaranStatusRepositoryImpl
import com.epy.linespotv2.data.repository_impl.parking.PostParkingRepositoryImpl
import com.epy.linespotv2.data.repository_impl.payment.PostPaymentParkingRepositoryImpl
import com.epy.linespotv2.data.repository_impl.riwayat.RiwayatRepositoryImpl
import com.epy.linespotv2.data.repository_impl.settings.SettingsRepositoryImpl
import com.epy.linespotv2.data.repository_impl.subscription.SubscribeRepositoryImpl
import com.epy.linespotv2.data.repository_impl.topup.TopUpRepositoryImpl
import com.epy.linespotv2.domain.repository.auth.AuthRepository
import com.epy.linespotv2.domain.repository.helper.GetLokasiRepository
import com.epy.linespotv2.domain.repository.helper.GetTarifRepository
import com.epy.linespotv2.domain.repository.helper.GetTopUpRepository
import com.epy.linespotv2.domain.repository.home.CustomerHomeRepository
import com.epy.linespotv2.domain.repository.home.JukirHomeRepository
import com.epy.linespotv2.domain.repository.laporan.LaporanRepository
import com.epy.linespotv2.domain.repository.payment.GetPembayaranStatusRepository
import com.epy.linespotv2.domain.repository.parking.PostParkingRepository
import com.epy.linespotv2.domain.repository.payment.PostPaymentParkingRepository
import com.epy.linespotv2.domain.repository.riwayat.RiwayatRepository
import com.epy.linespotv2.domain.repository.settings.SettingsRepository
import com.epy.linespotv2.domain.repository.subcription.SubscribeRepository
import com.epy.linespotv2.domain.repository.topup.TopUpRepository
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

    //auth
    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
    //home
    @Binds @Singleton
    abstract fun bindCustomerHomeRepository(impl: CustomerHomeRepositoryImpl): CustomerHomeRepository
    @Binds @Singleton
    abstract fun bindJukirHomeRepository(impl: JukirHomeRepositoryImpl): JukirHomeRepository
    //settings
    @Binds @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
    //scan
    @Binds @Singleton
    abstract fun bindScanRepository(impl: PostPaymentParkingRepositoryImpl): PostPaymentParkingRepository
    //subscribe
    @Binds @Singleton
    abstract fun bindSubscribeRepository(impl: SubscribeRepositoryImpl): SubscribeRepository
    //riwayat
    @Binds @Singleton
    abstract fun bindRiwayatRepository(impl: RiwayatRepositoryImpl): RiwayatRepository
    //helper
    @Binds @Singleton
    abstract fun bindGetLokasiRepository(impl: GetLokasiRepositoryImpl): GetLokasiRepository
    @Binds @Singleton
    abstract fun bindGetTarifRepository(impl: GetTarifRepositoryImpl): GetTarifRepository
    @Binds @Singleton
    abstract fun bindGetTopupRepository(impl: GetTopUpRepositoryImpl): GetTopUpRepository
    //laporan
    @Binds @Singleton
    abstract fun bindLaporanRepository(impl: LaporanRepositoryImpl): LaporanRepository
    //parking/payment
    @Binds @Singleton
    abstract fun bindInputManualRepository(impl: PostParkingRepositoryImpl): PostParkingRepository
    @Binds @Singleton
    abstract fun bindPembayaranStatusRepository(impl: GetPembayaranStatusRepositoryImpl): GetPembayaranStatusRepository
    //topup
    @Binds @Singleton
    abstract fun bindTopUpRepository(impl: TopUpRepositoryImpl): TopUpRepository

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

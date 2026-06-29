package com.epy.linespotv2.core.di

import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.data.repository.HomeJukirRepositoryImpl
import com.epy.linespotv2.data.repository.LoginRepositoryImpl
import com.epy.linespotv2.data.repository.RegisterRepositoryImpl
import com.epy.linespotv2.data.repository.GetLokasiRepositoryImpl
import com.epy.linespotv2.data.repository.InputManualRepositoryImpl
import com.epy.linespotv2.data.repository.LaporanRepositoryImpl
import com.epy.linespotv2.data.repository.PembayaranStatusRepositoryImpl
import com.epy.linespotv2.data.repository.RiwayatRepositoryImpl
import com.epy.linespotv2.data.repository.PostQrRepositoryImpl
import com.epy.linespotv2.data.repository.SubscribeRepositoryImpl
import com.epy.linespotv2.data.repository.UserRepositoryImpl
import com.epy.linespotv2.domain.repository.HomeJukirRepository
import com.epy.linespotv2.domain.repository.LoginRepository
import com.epy.linespotv2.domain.repository.RegisterRepository
import com.epy.linespotv2.domain.repository.GetLokasiRepository
import com.epy.linespotv2.domain.repository.InputManualRepository
import com.epy.linespotv2.domain.repository.LaporanRepository
import com.epy.linespotv2.domain.repository.PembayaranStatusRepository
import com.epy.linespotv2.domain.repository.PostQrRepository
import com.epy.linespotv2.domain.repository.SubscribeRepository
import com.epy.linespotv2.domain.repository.UserRepository
import com.epy.linespotv2.domain.repository.RiwayatRepository
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
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository
    @Binds @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
    @Binds @Singleton
    abstract fun bindHomeRepository(impl: HomeJukirRepositoryImpl): HomeJukirRepository
    @Binds @Singleton
    abstract fun bindRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository
    @Binds @Singleton
    abstract fun bindScanRepository(impl: PostQrRepositoryImpl): PostQrRepository
    @Binds @Singleton
    abstract fun bindSubscribeRepository(impl: SubscribeRepositoryImpl): SubscribeRepository
    @Binds @Singleton
    abstract fun bindRiwayatRepository(impl: RiwayatRepositoryImpl): RiwayatRepository
    @Binds @Singleton
    abstract fun bindGetLokasiRepository(impl: GetLokasiRepositoryImpl): GetLokasiRepository
    @Binds @Singleton
    abstract fun bindLaporanRepository(impl: LaporanRepositoryImpl): LaporanRepository
    @Binds @Singleton
    abstract fun bindInputManualRepository(impl: InputManualRepositoryImpl): InputManualRepository
    @Binds @Singleton
    abstract fun bindPembayaranStatusRepository(impl: PembayaranStatusRepositoryImpl): PembayaranStatusRepository

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

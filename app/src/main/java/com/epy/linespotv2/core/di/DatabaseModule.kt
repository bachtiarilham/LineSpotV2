package com.epy.linespotv2.core.di

import android.content.Context
import androidx.room.Room
import com.epy.linespotv2.core.database.AppDatabase
import com.epy.linespotv2.data.local.dao.HomeDao
//import com.epy.linespotv2.data.local.dao.LoginDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase (@ApplicationContext context: Context) : AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "linespot_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideHomeDao(database: AppDatabase): HomeDao {
        return database.homeDao()
    }
}
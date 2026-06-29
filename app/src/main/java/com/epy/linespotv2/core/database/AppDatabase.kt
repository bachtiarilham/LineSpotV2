package com.epy.linespotv2.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.epy.linespotv2.data.local.converter.HomeTypeConverters
import com.epy.linespotv2.data.local.dao.HomeDao
import com.epy.linespotv2.data.local.entity.HomeEntity

@TypeConverters(
    HomeTypeConverters::class
)
@Database(
    entities = [
        HomeEntity::class

       ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun loginDao () : LoginDao
    abstract fun homeDao() : HomeDao
}
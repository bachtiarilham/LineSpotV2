package com.epy.linespotv2.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epy.linespotv2.data.local.dao.HomeDao
import com.epy.linespotv2.data.local.entity.CustomerEntity
import com.epy.linespotv2.data.local.entity.JukirEntity


@Database(
    entities = [
        CustomerEntity::class,
        JukirEntity::class
       ],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun homeDao() : HomeDao
}

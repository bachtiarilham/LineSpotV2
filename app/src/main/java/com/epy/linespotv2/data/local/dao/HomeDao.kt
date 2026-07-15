package com.epy.linespotv2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epy.linespotv2.data.local.entity.CustomerEntity
import com.epy.linespotv2.data.local.entity.JukirEntity


@Dao
interface HomeDao {
    @Query("SELECT * FROM customer_cache LIMIT 1")
    suspend fun getCustomerCache(): CustomerEntity?

    @Query("SELECT * FROM jukir_cache LIMIT 1")
    suspend fun getJukirCache(): JukirEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomerCache(cache: CustomerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJukirCache(cache: JukirEntity)

    @Query("DELETE FROM customer_cache")
    suspend fun clearCustomerCache()

    @Query("DELETE FROM jukir_cache")
    suspend fun clearJukirCache()
}

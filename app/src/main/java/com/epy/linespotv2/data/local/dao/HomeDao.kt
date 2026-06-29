package com.epy.linespotv2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epy.linespotv2.data.local.entity.HomeEntity

@Dao
interface HomeDao {
    @Query("SELECT * FROM home_cache")
    suspend fun getHomeCache(): HomeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeCache(cache: HomeEntity)

    @Query("DELETE FROM home_cache")
    suspend fun clearCache()
}
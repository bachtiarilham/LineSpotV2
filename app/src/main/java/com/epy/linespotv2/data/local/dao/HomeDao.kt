package com.epy.linespotv2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epy.linespotv2.data.local.entity.HomeEntity

@Dao
interface HomeDao {
    @Query(
        """
        SELECT
            profileId,
            profileName,
            photoUrl,
            0 AS saldo,
            expiredDate,
            0 AS pendapatan,
            lokasi,
            area,
            zona,
            events,
            news,
            warningProfile,
            warningParking,
            warningFinance,
            cachedAt
        FROM home_cache
        """
    )
    suspend fun getHomeCache(): HomeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeCache(cache: HomeEntity)

    @Query("DELETE FROM home_cache")
    suspend fun clearCache()
}

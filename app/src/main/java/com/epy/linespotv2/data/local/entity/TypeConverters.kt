package com.epy.linespotv2.data.local.entity

import androidx.room.TypeConverter
import com.epy.linespotv2.domain.model.HomeEventItem
import com.epy.linespotv2.domain.model.HomeNewsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {
    private val gson = Gson()

    // --- Converter untuk List<HomeEventItem> ---
    @TypeConverter
    fun fromHomeEventItemList(list: List<HomeEventItem>?): String? {
        return if (list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun toHomeEventItemList(json: String?): List<HomeEventItem>? {
        return if (json == null) null else gson.fromJson(json, object : TypeToken<List<HomeEventItem>>() {}.type)
    }

    // --- Converter untuk List<HomeNewsItem> ---
    @TypeConverter
    fun fromHomeNewsItemList(list: List<HomeNewsItem>?): String? {
        return if (list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun toHomeNewsItemList(json: String?): List<HomeNewsItem>? {
        return if (json == null) null else gson.fromJson(json, object : TypeToken<List<HomeNewsItem>>() {}.type)
    }
}
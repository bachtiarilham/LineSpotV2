package com.epy.linespotv2.data.local.converter

import androidx.room.TypeConverter
import com.epy.linespotv2.domain.model.home.HomeEventItem
import com.epy.linespotv2.domain.model.home.HomeNewsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromEventList(events: List<HomeEventItem>): String =
        gson.toJson(events)

    @TypeConverter
    fun toEventList(json: String): List<HomeEventItem> =
        gson.fromJson(json, object : TypeToken<List<HomeEventItem>>() {}.type)
            ?: emptyList()

    @TypeConverter
    fun fromNewsList(news: List<HomeNewsItem>): String =
        gson.toJson(news)

    @TypeConverter
    fun toNewsList(json: String): List<HomeNewsItem> =
        gson.fromJson(json, object : TypeToken<List<HomeNewsItem>>() {}.type)
            ?: emptyList()
}
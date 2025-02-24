package com.jer.banyumastourismapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jer.banyumastourismapp.domain.model.City
import com.jer.banyumastourismapp.domain.model.Plan

class ItsTypeConverter {
    private val gson = Gson()


    @TypeConverter
    fun fromCityList(value: List<City>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCityList(value: String): List<City> {
        val type = object : TypeToken<List<City>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromNestedPlanList(value: List<List<Plan>>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toNestedPlanList(value: String): List<List<Plan>> {
        val type = object : TypeToken<List<List<Plan>>>() {}.type
        return gson.fromJson(value, type)
    }
}
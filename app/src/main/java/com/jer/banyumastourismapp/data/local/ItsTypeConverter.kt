package com.jer.banyumastourismapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jer.banyumastourismapp.domain.model.City
import com.jer.banyumastourismapp.domain.model.Facility
import com.jer.banyumastourismapp.domain.model.Plan

@ProvidedTypeConverter
class ItsTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromFacilityList(value: List<Facility>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toFacilityList(value: String): List<Facility> {
        val type = object : TypeToken<List<Facility>>() {}.type
        return gson.fromJson(value, type)
    }


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
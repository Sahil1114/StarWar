package com.example.starwar.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertor {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if (value == null) {
            return null
        }

        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(value: List<String>?): String? {
        return Gson().toJson(value)
    }
}
package com.openclassrooms.realestatemanager.data.dao.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class StringConverter {

    @TypeConverter
    fun from(string: String?): ArrayList<String> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<String>>() {}.type)
    }

    @TypeConverter
    fun to(list: ArrayList<String>): String {
        return Gson().toJson(list)
    }
}
package com.openclassrooms.realestatemanager.data.dao.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity

class PictureEntityConverter {

    @TypeConverter
    fun from(string: String?): List<PictureEntity> {
        return Gson().fromJson(string, object : TypeToken<List<PictureEntity>>() {}.type)
    }

    @TypeConverter
    fun to(list: List<PictureEntity>): String {
        return Gson().toJson(list)
    }
}
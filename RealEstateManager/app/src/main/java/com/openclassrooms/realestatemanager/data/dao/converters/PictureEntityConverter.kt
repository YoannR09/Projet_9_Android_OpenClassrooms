package com.openclassrooms.realestatemanager.data.dao.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity

class PictureEntityConverter {

    @TypeConverter
    fun from(string: String?): ArrayList<PictureEntity> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<PictureEntity>>() {}.type)
    }

    @TypeConverter
    fun to(list: ArrayList<PictureEntity>): String {
        return Gson().toJson(list)
    }
}
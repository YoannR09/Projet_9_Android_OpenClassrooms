package com.openclassrooms.realestatemanager.data.dao.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import java.lang.reflect.Type

//@ProvidedTypeConverter
object PictureEntityConverter {

    @TypeConverter
    fun stringToPicture(string: String?): Array<PictureEntity> {
        val listType: Type = object : TypeToken<ArrayList<PictureEntity?>?>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun pictureToString(list: ArrayList<PictureEntity?>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
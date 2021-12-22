package com.openclassrooms.realestatemanager.data.dao.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity

@ProvidedTypeConverter
object PictureEntityConverter {

        @TypeConverter
        fun stringToPicture(string: String?): PictureEntity? {
            return PictureEntity(url = "string", description = "des", name = "name")
        }

        @TypeConverter
        fun pictureToString(example: PictureEntity?): String? {
            return "${example?.name}|${example?.description}|${example?.url}"
        }
}
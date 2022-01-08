package com.openclassrooms.realestatemanager.data.dao.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity


object PictureEntityConverter {


        fun stringToPicture(string: String?): List<PictureEntity> {
            return listOf() // todo
            // return PictureEntity(id = "id", url = "string", description = "des", name = "name")
        }


        fun pictureToString(example: List<PictureEntity?>): String {
            return ""//"${example?.name}|${example?.description}|${example?.url}"
        }
}
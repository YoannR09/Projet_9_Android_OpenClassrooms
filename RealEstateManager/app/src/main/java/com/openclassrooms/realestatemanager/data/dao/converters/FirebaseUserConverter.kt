package com.openclassrooms.realestatemanager.data.dao.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

class FirebaseUserConverter {
    @TypeConverter
    fun from(string: String?): FirebaseUser {
        return Gson().fromJson(string, object : TypeToken<FirebaseUser>() {}.type)
    }

    @TypeConverter
    fun to(obj: FirebaseUser): String {
        return Gson().toJson(obj)
    }
}
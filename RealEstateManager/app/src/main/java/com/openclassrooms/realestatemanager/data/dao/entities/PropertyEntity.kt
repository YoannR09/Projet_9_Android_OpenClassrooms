package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.utils.PropertiesType
import com.openclassrooms.realestatemanager.utils.PropertyState

@Entity
data class PropertyEntity(
        @PrimaryKey(autoGenerate = true) var id: Int,
        val type: String = PropertiesType.APPARTMENT.name,
        val price: Int = 0,
        val meter: Int = 0,
        val pieces: Int = 0,
        val description: String = "",
        //val picturesList: ArrayList<PictureEntity> = ArrayList(),
        val address: String = "",
        //@Embedded val interestPoints: ArrayList<InterestPoint>,
        val state: String = PropertyState.AVAILABLE.name,
        val createDate: String,
        val soldDate: String,
        //@Embedded val agent: FirebaseUser
)



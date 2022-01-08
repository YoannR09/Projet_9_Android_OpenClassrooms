package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.utils.InterestPoint
import com.openclassrooms.realestatemanager.utils.Picture
import com.openclassrooms.realestatemanager.utils.PropertiesType
import com.openclassrooms.realestatemanager.utils.PropertyState
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class PropertyEntity(
        @PrimaryKey(autoGenerate = true) var id: Int,
        val type: String = PropertiesType.APPARTMENT.name,
        val price: Int = 0,
        val meter: Int = 0,
        val pieces: Int = 0,
        val description: String = "",
        //val picturesList: List<PictureEntity> = ArrayList(),
        val address: String = "",
        //@Embedded val interestPoints: ArrayList<InterestPoint>,
        val state: String = PropertyState.AVAILABLE.name,
        //@Embedded val createDate: Date,
        //@Embedded val soldDate: Date,
        //@Embedded val agent: FirebaseUser
)



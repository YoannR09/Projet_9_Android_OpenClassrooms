package com.openclassrooms.realestatemanager.domain.repositories

import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.PropertiesFirebaseApi
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.utils.hasInternet

class Repository {

    companion object {

        val propertyRepository: PropertyRepository by lazy {
            PropertyRepository(PropertiesFirebaseApi(), propertiesDaoRoom!!)
        }
        private val propertiesDaoRoom by lazy {
            RealEstateManagerDatabase.instance.propertiesDaoRoom()
        }
    }
}
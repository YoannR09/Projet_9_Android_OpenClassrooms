package com.openclassrooms.realestatemanager.domain.repositories

import android.content.Context
import com.openclassrooms.realestatemanager.data.dao.PropertiesDaoImplFirebase
import com.openclassrooms.realestatemanager.data.dao.PropertiesDaoRoom
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.utils.hasInternet

class Repository {

    companion object {

        private var propertyRepository: PropertyRepository? = null
        private var localProperyRepository: PropertyRepository? = null
        private val propertiesDaoRoom by lazy {
            RealEstateManagerDatabase.instance.propertiesDaoRoom()
        }

        private fun createPropertyRepo() {
            propertyRepository = PropertyRepository(PropertiesDaoImplFirebase())
        }

        private fun createLocalPropertyRepo() {
            localProperyRepository = PropertyRepository(propertiesDaoRoom!!)
        }

        fun getPropertyRepository(context: Context?): PropertyRepository? {
            return if(context?.hasInternet == true) {
                if(propertyRepository == null) {
                    createPropertyRepo()
                }
                propertyRepository
            } else {
                if(localProperyRepository == null) {
                    createLocalPropertyRepo()
                }
                localProperyRepository
            }
        }
    }
}
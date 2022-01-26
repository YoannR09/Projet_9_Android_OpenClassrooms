package com.openclassrooms.realestatemanager.domain.repositories

import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.PropertiesDaoImplFirebase
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

        fun getPropertyRepository(): PropertyRepository? {
            return if(RealStateManagerApplication.context.hasInternet) {
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
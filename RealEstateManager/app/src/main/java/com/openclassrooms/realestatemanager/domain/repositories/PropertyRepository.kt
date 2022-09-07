package com.openclassrooms.realestatemanager.domain.repositories

import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.PropertiesDao
import com.openclassrooms.realestatemanager.data.dao.PropertiesFirebaseApi
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.mappers.asModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.utils.Utils.isNetworkAvailable

suspend fun <T> get(remote: suspend () -> T, local: suspend () -> T) : Result<T>{
    val localCall = suspend {
        try {
            Result.success(local())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    return try {
        if(isNetworkAvailable(RealStateManagerApplication.context)) {
            Result.success(remote())
        } else localCall()
    } catch (e: Exception){
        e.printStackTrace()
        localCall()
    }
}

suspend fun <T> set(remote: suspend () -> T, local: suspend () -> T) {
    try {
        if(isNetworkAvailable(RealStateManagerApplication.context)) {
            remote()
        }
        local()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

class PropertyRepository(
    private val api: PropertiesFirebaseApi,
    private val dao: PropertiesDao,
) {

    suspend fun getList(): Result<List<PropertyModel>> {
        return get(
            remote = api::list,
            local = dao::list
        ).map { it.map(PropertyEntity::asModel) }
    }

    suspend fun getPropertyById(id: String): Result<PropertyModel> {
        return get(
            remote = {
                api.getPropertyById(id)
            },
            local = {
                dao.getPropertyById(id)
            }
        ).map { it.asModel() }
    }

    suspend fun createProperty(propertyEntity: PropertyEntity) {
        return set(
            remote = {
                api.createProperty(propertyEntity)
            },
            local = {
                dao.setProperty(propertyEntity)
            }
        )
    }

    suspend fun updateStateProperty(state: String, date: String, propertyId: String) {
        return set(
            remote = {
                api.updateStateProperty(state, date, propertyId)
            },
            local = {
                dao.updateStateProperty(state, date, propertyId)
            }
        )
    }

    suspend fun updateProperty(propertyEntity: PropertyEntity) {
        return set(
            remote = {
                api.updateProperty(propertyEntity)
            },
            local = {
                dao.setProperty(propertyEntity)
            }
        )
    }
}
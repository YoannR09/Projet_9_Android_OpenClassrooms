package com.openclassrooms.realestatemanager.data.dao

import android.content.res.Resources
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class PropertiesDaoImplFirebase : PropertiesDao {

    private val db = Firebase.firestore

    override suspend fun list(): List<PropertyEntity> {
        return suspendCoroutine {
            db.collection("property")
                .get()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        it.resume(task.result?.documents?.mapNotNull {
                            it.toObject(PropertyEntity::class.java)
                        }?: listOf())
                    } else {
                        it.resume(listOf())
                    }
                }
                .addOnFailureListener {
                        err ->
                    err.printStackTrace()
                }
        }
    }

    override suspend fun createProperty(entity: PropertyEntity?) {
        return suspendCoroutine {
            db.collection("property")
                .add(entity!!)
                .addOnCompleteListener { result ->
                    if(result.isSuccessful) {
                        it.resume(Unit)
                    } else {
                        it.resume(Unit)
                    }
                }
                .addOnFailureListener { err ->
                    err.printStackTrace()
                }
        }
    }

    override suspend fun getPropertyById(id: String): PropertyEntity {
        return suspendCoroutine { coroutine ->
            db.collection("property")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        coroutine.resume(task.result!!.documents.first().toObject(PropertyEntity::class.java)!!)
                    }else{
                        throw Resources.NotFoundException()
                    }
                }
                .addOnFailureListener { err ->
                    err.printStackTrace()
                    throw Resources.NotFoundException()
                }
        }
    }

    override suspend fun updateStateProperty(state: String, date: String, propertyId: String) {
        return suspendCoroutine {
                coroutine ->
            db.collection("property").document(propertyId).update(
                "state", state,
                "soldDate", date)
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                        err ->
                    err.printStackTrace()
                    throw Resources.NotFoundException()
                }
        }
    }
    //override
    suspend fun updateProperty(propertyEntity: PropertyEntity) {
        return suspendCoroutine {
                coroutine ->
            db.collection("property").document(propertyEntity.id)
                .update("description", propertyEntity.description,
                    "interestPoint", propertyEntity.interestPoints,
                    "meter", propertyEntity.meter,
                    "picturesList", propertyEntity.picturesList,
                    "pieces", propertyEntity.pieces,
                    "price", propertyEntity.price,
                    "type", propertyEntity.type)
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                        err ->
                    err.printStackTrace()
                    throw Resources.NotFoundException()
                }
        }
    }

    /**
     * Shouldn't be impl on this project
     */
    override fun deleteProperty(id: String) {
        TODO("Not yet implemented")
    }

}
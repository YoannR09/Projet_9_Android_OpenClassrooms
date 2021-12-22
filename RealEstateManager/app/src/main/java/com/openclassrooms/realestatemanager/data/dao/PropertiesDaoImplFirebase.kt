package com.openclassrooms.realestatemanager.data.dao

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PropertiesDaoImplFirebase : PropertiesDao {

    private val db = Firebase.firestore

    override suspend fun list(): List<PropertyEntity> {
        return suspendCoroutine {
            db.collection("property")
                    .get()
                    .addOnCompleteListener {
                        task ->
                        if(task.isSuccessful) {
                            it.resume(listOf())
                        } else {
                            it.resume(listOf())
                        }
                    }
        }
    }

    override suspend fun createProperty(entity: PropertyEntity?) {
        return suspendCoroutine {
            db.collection("property")
                    .add(entity!!)
                    .addOnCompleteListener {
                        result ->
                        if(result.isSuccessful) {
                            it.resume(Unit)
                        } else {
                            it.resume(Unit)
                        }
                    }
        }
    }

    /**
     * Shouldn't be impl on this project
     */
    override fun deleteProperty(id: String?) {
        TODO("Not yet implemented")
    }

}
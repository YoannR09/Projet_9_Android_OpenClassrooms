package com.openclassrooms.realestatemanager

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase

class RealStateManagerApplication : Application() {

    companion object {
        lateinit var context: Application
        val contextApp: Context get() = context
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Firebase.firestore.firestoreSettings = FirebaseFirestoreSettings
            .Builder()
            .setPersistenceEnabled(false)
            .build()
        RealEstateManagerDatabase.createInstance(this)
        context = this
    }


}
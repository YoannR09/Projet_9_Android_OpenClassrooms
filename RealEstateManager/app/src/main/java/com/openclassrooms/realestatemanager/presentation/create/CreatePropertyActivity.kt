package com.openclassrooms.realestatemanager.presentation.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R

class CreatePropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_create_property)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
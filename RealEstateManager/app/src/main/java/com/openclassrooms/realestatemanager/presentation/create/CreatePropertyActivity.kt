package com.openclassrooms.realestatemanager.presentation.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import java.util.*
import kotlin.collections.ArrayList

class CreatePropertyActivity : AppCompatActivity() {

    lateinit var button: Button
    val viewModel by lazy {
        ViewModelProvider(this)[CreatePropertyActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(R.layout.activity_create_property)
        button = findViewById(R.id.test_add)
        button.setOnClickListener {
            viewModel.createProperty(
                PropertyEntity(
                    id = 3,
                    name = "name",
                    "type",
                    45000,
                    250,
                    4,
                    "descr",
                    ArrayList(),
                    "ad",
                    ArrayList(),
                    "free",
                    Date().toString(),
                    Date().toString(),
                    "user"
                )
            )
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
package com.openclassrooms.realestatemanager.presentation.create

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.utils.observe

class CreatePropertyActivity : AppCompatActivity() {

    private val previous: Button get() = findViewById(R.id.create_button_previous)
    private val next: Button get() = findViewById(R.id.create_button_next)
    private val confirm: Button get() = findViewById(R.id.create_button_confirm)

    private val stepperTitle: TextView get() = findViewById(R.id.stepper_title)
    private val stepperPercent: TextView get() = findViewById(R.id.stepper_percent)
    private val stepper: LinearProgressIndicator get() = findViewById(R.id.stepper)

    private val appBar: MaterialToolbar get() = findViewById(R.id.top_bar)

    private val generalInfoStepper by lazy(::GeneralInfoStepFragment)
    private val purchaseInfoStepFragment by lazy(::PurchaseInfoStepFragment)
    private val picturesStepFragment by lazy(::PicturesStepFragment)
    private val confirmStepFragment by lazy(::ConfirmStepFragment)

    val interestSelected: ArrayList<String> = ArrayList()

    private val fm: FragmentManager = supportFragmentManager
    var previousStep: Int = 0

    val viewModel by lazy {
        ViewModelProvider(this)[CreatePropertyActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_property)

        setSupportActionBar(appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        appBar.setTitleTextColor(Color.WHITE)

        viewModel.nextButtonIsEnabled.observe(this) {
            next.isEnabled = it
        }

        viewModel.stepperTitle.observe(this) {
            stepperTitle.text = it
        }

        viewModel.stepperPercent.observe(this) {
            stepperPercent.text = "$it%"
        }

        viewModel.stepperPercent.observe(this) {
            stepper.setProgressCompat(it,true)
        }

        viewModel.previousStep.observe(this, previous::setVisibility)

        viewModel.nextStep.observe(this, next::setVisibility)

        viewModel.confirmButton.observe(this, confirm::setVisibility)

        viewModel.currentStep.observe(this) {
            val ft = fm.beginTransaction()

            when {
                it > previousStep ->  ft.setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                else -> ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            }

            when (it) {
                0 -> ft.replace(R.id.create_container, generalInfoStepper)
                1 -> ft.replace(R.id.create_container, purchaseInfoStepFragment)
                2 -> ft.replace(R.id.create_container, picturesStepFragment)
                3 -> ft.replace(R.id.create_container, confirmStepFragment)
            }
            previousStep = it
            ft.commit()
        }

        previous.setOnClickListener {
            viewModel.currentStep.value = viewModel.currentStep.value!! - 1
        }

        next.setOnClickListener {
            viewModel.currentStep.value = viewModel.currentStep.value!! + 1
        }

        confirm.setOnClickListener {
            val startActivity = {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            viewModel.createProperty(startActivity)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
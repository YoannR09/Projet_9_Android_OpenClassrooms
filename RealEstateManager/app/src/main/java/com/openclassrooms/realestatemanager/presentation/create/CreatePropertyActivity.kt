package com.openclassrooms.realestatemanager.presentation.create

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.utils.PropertyState
import com.openclassrooms.realestatemanager.utils.observe

class CreatePropertyActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, property: PropertyModel? = null) {
            val intent = Intent(context, CreatePropertyActivity::class.java)
            intent.putExtra("property", property)
            context.startActivity(intent)
        }
    }

    private val previous: Button get() = findViewById(R.id.create_button_previous)
    private val next: Button get() = findViewById(R.id.create_button_next)
    private val confirm: Button get() = findViewById(R.id.create_button_confirm)

    private val stepperTitle: TextView get() = findViewById(R.id.stepper_title)
    private val stepperPercent: TextView get() = findViewById(R.id.stepper_percent)
    private val stepper: LinearProgressIndicator get() = findViewById(R.id.stepper)

    private val appBar: MaterialToolbar get() = findViewById(R.id.top_bar)
    private val sellToggle: MaterialButtonToggleGroup get() = findViewById(R.id.sell_property_button)
    private val sellBar: LinearLayout get() = findViewById(R.id.sell_bar)
    private val generalInfoStepper by lazy(::GeneralInfoStepFragment)
    private val purchaseInfoStepFragment by lazy(::PurchaseInfoStepFragment)
    private val picturesStepFragment by lazy(::PicturesStepFragment)
    private val confirmStepFragment by lazy(::ConfirmStepFragment)

    private val fm: FragmentManager = supportFragmentManager
    var previousStep: Int = 0

    val viewModel by lazy {
        ViewModelProvider(this, CreatePropertyActivityViewModelFactory(
            property = intent.getSerializableExtra("property") as? PropertyModel
        ))[CreatePropertyActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_property)

        setSupportActionBar(appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        appBar.setTitleTextColor(Color.WHITE)

        val availableId = sellToggle[0].id
        val sellId = sellToggle[1].id

        if(viewModel.property != null) {
            if(viewModel.property!!.state == "AVAILABLE") {
                sellToggle.check(availableId)
            } else {
                sellToggle.check(sellId)
            }
            sellBar.visibility = View.VISIBLE
        } else {
            sellBar.visibility = View.GONE
        }

        sellToggle.addOnButtonCheckedListener{ group, _, isCheck ->
            // val oldChecked = group.checkedButtonId
            if(isCheck) {
                if(group.checkedButtonId == availableId) {
                    group.check(availableId)
                    viewModel.updateState(PropertyState.AVAILABLE.name, null)
                } else {
                    group.check(sellId)
                    val datePicker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Property sell date")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build()
                    println("create picker here")
                    datePicker.show(supportFragmentManager, "TAG")
                    datePicker.addOnPositiveButtonClickListener {
                            date ->
                        viewModel.updateState(PropertyState.SELL.name, (date / 1000).toString())
                    }
                    datePicker.addOnCancelListener{
                        // sellToggle.check(availableId)
                    }

                }
            }
        }

        implScreenState()

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

        viewModel.nextStepIsVisible.observe(this, {
            next.visibility = when(it) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        })

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
            viewModel.currentStep.value = viewModel.currentStep.value - 1
        }

        next.setOnClickListener {
            viewModel.currentStep.value = viewModel.currentStep.value + 1
        }

        confirm.setOnClickListener {
            val startActivity = {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            viewModel.createProperty(startActivity)
        }
    }

    private fun implScreenState() {
        viewModel
            .screenState
            .observe(this) { state ->
                when(state) {
                    is ScreenStateError -> {
                        val toast = Toast.makeText(this,
                            "Error, try again", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    ScreenStateLoading -> {}
                    ScreenStateNothing -> {}
                    is ScreenStateSuccess -> {}
                    ScreenStateNoData -> {

                    }
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
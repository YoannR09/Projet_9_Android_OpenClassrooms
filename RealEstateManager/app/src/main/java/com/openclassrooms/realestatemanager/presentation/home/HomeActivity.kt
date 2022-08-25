package com.openclassrooms.realestatemanager.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivity
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.FilterPropertyDialogFragment
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.MapsFragment
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyListFragment
import com.openclassrooms.realestatemanager.utils.Utils.isNetworkAvailable
import com.openclassrooms.realestatemanager.utils.observe


enum class HomeFragmentState {
    LIST,
    MAP
}

class HomeActivity : AppCompatActivity() {
    private val DRAWER_ICON_ID = 16908332
    val viewModel: HomeActivityViewModel by lazy {
        ViewModelProvider(this)[HomeActivityViewModel::class.java]
    }

    private val toolbar: MaterialToolbar get() = findViewById(R.id.tool_bar)
    private val agent: TextView get() = navigationView.getHeaderView(0).findViewById(R.id.agent_title)
    private val drawer: DrawerLayout get() = findViewById(R.id.drawer)
    private val navigationView: NavigationView get() = findViewById(R.id.navigation_view)
    private val leaveButton: Button get() =  navigationView.getHeaderView(0).findViewById(R.id.leave_button)
    private val filterButton: Button get() = findViewById(R.id.filter_button)
    private val mapButton: Button get() = findViewById(R.id.map_button)
    private val listButton: Button get() = findViewById(R.id.list_button)
    private val listFragment: PropertyListFragment by lazy {
        PropertyListFragment()
    }
    private val mapFragment: MapsFragment by lazy {
        MapsFragment()
    }
    private var active: Fragment? = null

    private val fm: FragmentManager get() = supportFragmentManager

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()) { result: FirebaseAuthUIAuthenticationResult? -> this.onSignInResult(result!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser == null) {
            val providers: List<AuthUI.IdpConfig> = listOf(
                AuthUI.IdpConfig.EmailBuilder().build()
                //AuthUI.IdpConfig.GoogleBuilder().build(),
                //AuthUI.IdpConfig.TwitterBuilder().build(),
            )
            val signInIntent: Intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                //.setTheme(R.style.LoginTheme)
                //.setLogo(R.drawable.main_logo)
                .build()
            signInLauncher.launch(signInIntent)
        } else {
            initView()
        }

    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fm.beginTransaction().add(R.id.container_fragment, mapFragment, "MAP")
            .hide(mapFragment)
            .commit()
        fm.beginTransaction().add(R.id.container_fragment, listFragment, "LIST")
            .hide(listFragment)
            .commit()
        viewModel.mapButton.observe(this, action = mapButton::setVisibility)
        viewModel.listButton.observe(this, action = listButton::setVisibility)
        viewModel.fragmentState.observe(this) { state ->
            active = when(state) {
                HomeFragmentState.LIST ->  {
                    fm.beginTransaction().apply {
                        active?.let {
                            hide(it)
                        }
                    }.show(listFragment).commit()
                    listFragment
                }
                HomeFragmentState.MAP -> {
                    fm.beginTransaction().apply {
                        active?.let {
                            hide(it)
                        }
                    }.show(mapFragment).commit()
                    mapFragment
                }
            }
        }
        listButton.setOnClickListener {
            viewModel.fragmentState.value = HomeFragmentState.LIST
        }
        filterButton.setOnClickListener {
            showDialog()
        }
        mapButton.setOnClickListener {
            viewModel.fragmentState.value = HomeFragmentState.MAP
        }
        leaveButton.setOnClickListener {
            logoutToRefreshMainActivity()
        }
        agent.text = if(viewModel.isAnonymous.value) {
            "anonymous"
        } else {
            FirebaseAuth.getInstance().currentUser?.email
        }
    }

    /**
     * This method has called after signed result from firebase auth
     * @param result: FirebaseAuthUIAuthenticationResult
     */
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            this.initView()
        } else {
            val toast = Toast.makeText(RealStateManagerApplication.contextApp,
                getString(R.string.error_login), Toast.LENGTH_LONG)
            toast.show()
            logoutToRefreshMainActivity()
        }
    }

    private fun showDialog() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        // Create and show the dialog.
        val newFragment: FilterPropertyDialogFragment = FilterPropertyDialogFragment.newInstance()
        newFragment.show(ft, "dialog")
    }

    /**
     * This method return to Freibase auth activity
     * And lohout if current firebase session has active
     */
    private fun logoutToRefreshMainActivity() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_create -> {
                val intent = Intent(this, CreatePropertyActivity::class.java)
                startActivity(intent)
            }
            DRAWER_ICON_ID -> {
                drawer.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
package com.openclassrooms.realestatemanager.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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


class HomeActivity : AppCompatActivity() {
    private val DRAWER_ICON_ID = 16908332
    val viewModel: HomeActivityViewModel by lazy {
        ViewModelProvider(this)[HomeActivityViewModel::class.java]
    }
    val sharedViewModel by lazy {

    }
    lateinit var toolbar: MaterialToolbar
    lateinit var agent: TextView
    lateinit var drawer: DrawerLayout
    lateinit var largeScreenList: View
    lateinit var navigationView: NavigationView
    lateinit var leaveButton: Button
    private lateinit var filterButton: Button
    private lateinit var mapButton: Button

    private var listFragment: PropertyListFragment = PropertyListFragment()
    private var mapFragment: MapsFragment = MapsFragment()
    private var active: Fragment? = null

    private val fm: FragmentManager = supportFragmentManager

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
            this.initView()
        }
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        for (fragment in fm.fragments) {
            fm.beginTransaction().remove(fragment).commit()
        }
        fm.beginTransaction().add(R.id.container_fragment, mapFragment, "MAP")
            .hide(mapFragment)
            .commit()
        fm.beginTransaction().add(R.id.container_fragment, listFragment, "LIST")
            .hide(listFragment)
            .commit()
        if(active == null) {
            fm.beginTransaction().show(listFragment).commit();
            active = listFragment
        }
        filterButton = findViewById(R.id.filter_button)
        filterButton.setOnClickListener {
            showDialog()
        }
        mapButton = findViewById(R.id.map_button)
        mapButton.setOnClickListener {
            fm.beginTransaction().hide(active!!).show(mapFragment).commit();
            active = mapFragment;
        }
        toolbar = findViewById(R.id.tool_bar)
        drawer = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigation_view)
        agent = navigationView.getHeaderView(0).findViewById(R.id.agent_title)
        leaveButton = navigationView.getHeaderView(0).findViewById(R.id.leave_button)
        leaveButton.setOnClickListener {
            logoutToRefreshMainActivity()
        }
        agent.text = FirebaseAuth.getInstance().currentUser?.email
        var isLargeScreen: Boolean
        try {
            largeScreenList = findViewById(R.id.large_screen_list)
            isLargeScreen = true
        }catch (e: NullPointerException) {
            isLargeScreen = false
        }

        viewModel.isLargeScreen = isLargeScreen
        setSupportActionBar(toolbar)
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
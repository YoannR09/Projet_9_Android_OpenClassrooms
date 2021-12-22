package com.openclassrooms.realestatemanager.presentation.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealeStateManagerApplication

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeActivityViewModel

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
        viewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)
        setContentView(R.layout.activity_main)
    }

    /**
     * This method has called after signed result from firebase auth
     * @param result: FirebaseAuthUIAuthenticationResult
     */
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            this.initView()
        } else {
            val toast = Toast.makeText(RealeStateManagerApplication.getContextApp(),
                    getString(R.string.error_login), Toast.LENGTH_LONG)
            toast.show()
            logoutToRefreshMainActivity()
        }
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
}
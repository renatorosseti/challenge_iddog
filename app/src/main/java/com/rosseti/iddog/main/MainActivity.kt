package com.rosseti.iddog.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rosseti.iddog.R
import com.rosseti.iddog.dialog.ErrorDialog
import com.rosseti.iddog.dialog.ProgressDialog
import com.rosseti.iddog.util.InternetUtil
import com.rosseti.iddog.util.InternetViewState
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var internetUtil: InternetUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_husky, R.id.navigation_hound, R.id.navigation_pug, R.id.navigation_labrador
            )
        )
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.label == getString(R.string.title_husky)) {
                Log.i("Main Activity","Teste")
                val argument = NavArgument.Builder().setDefaultValue("Renato").build()
                destination.addArgument("Argument", argument)
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        observeInternetUtil()
    }

    fun observeInternetUtil() {
        internetUtil.observe(this, Observer {
            when (it) {
                is InternetViewState.HasNoInternet -> {
                    ProgressDialog.hide()
                    ErrorDialog.show(context = this, message = getString(R.string.error_internet))
                }
            }
        })
    }
}

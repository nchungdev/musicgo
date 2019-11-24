package com.nchungdev.musicgo.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nchungdev.musicgo.R

fun AppCompatActivity.setupNavigationUI(menuItems: Set<Int>) {
    val navView: BottomNavigationView = findViewById(R.id.nav_view)
    val navController = findNavController(R.id.nav_host_fragment)
    val appBarConfiguration = AppBarConfiguration(menuItems)

    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)
    navView.selectedItemId = R.id.navigation_dashboard
}

fun Intent?.getActionNonNull(): String {
    return this?.action ?: ""
}

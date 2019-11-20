package com.nchungdev.musicgo

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

fun AppCompatActivity.setupNavigationUI(menuItems: Set<Int>) {
    val navView: BottomNavigationView = findViewById(R.id.nav_view)
    val navController = findNavController(R.id.nav_host_fragment)
    val appBarConfiguration = AppBarConfiguration(menuItems)

    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)
}
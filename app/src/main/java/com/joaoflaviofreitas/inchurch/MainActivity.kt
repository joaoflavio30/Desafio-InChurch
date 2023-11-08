package com.joaoflaviofreitas.inchurch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joaoflaviofreitas.inchurch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setOnItemSelectedListener { menuItem ->
            return@setOnItemSelectedListener if (navController.currentDestination?.id == R.id.navigation_details) {
                if (menuItem.itemId == R.id.navigation_favorites) {
                    navController.navigate(R.id.navigation_favorites)
                } else {
                    navController.navigate(R.id.navigation_home)
                }
                true
            } else if (menuItem.itemId == R.id.navigation_favorites) {
                navController.navigate(R.id.navigation_favorites)
                true
            } else {
                navController.navigate(R.id.navigation_home)
                true
            }
        }
    }
}

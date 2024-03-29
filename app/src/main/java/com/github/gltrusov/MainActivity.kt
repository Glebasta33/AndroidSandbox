package com.github.gltrusov

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.github.gltrusov.navigation.graph.AppNavGraph

/**
 * Главная стартовая активность приложения.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        setupActionBar()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        AppNavGraph.setGraph(navController)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (navController.currentBackStack.value.isNotEmpty()) {
                navController.popBackStack()
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
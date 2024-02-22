package com.github.gltrusov.navigation.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.github.gltrusov.R

/**
 * Базовый фрагмент, содержащий основную повторяюущюся логику в приложении.
 */
open class CoreFragment : Fragment() {

    /**
     * Контроллер для управления навигацией.
     */
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavController()
    }

    override fun onResume() {
        super.onResume()
        updateActionBar()
    }

    private fun setNavController() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    private fun updateActionBar() {
        val className = this::class.java.simpleName
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = className
        }
    }
}
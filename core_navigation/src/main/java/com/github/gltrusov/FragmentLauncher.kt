package com.github.gltrusov

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.reflect.KClass

interface FragmentLauncher {

    val fragmentClass: KClass<out Fragment>
    val fragmentTag: String

    fun launch(fragmentManager: FragmentManager, @IdRes containerViewId: Int) {
        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(containerViewId, fragmentClass.java, null, fragmentTag)
            .addToBackStack(BACKSTACK_NAME)
            .commit()
    }

    private companion object {
        const val BACKSTACK_NAME = "FragmentLauncherBackStack"
    }
}
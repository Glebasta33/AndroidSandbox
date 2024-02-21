package com.github.gltrusov.navigation

import android.app.Activity
import android.app.PendingIntent
import androidx.navigation.NavController
import androidx.navigation.NavGraph

/**
 * ## Create a deep link for a destination.
 * [Docs](https://developer.android.com/guide/navigation/design/deep-link)
 *
 * Create an explicit deep link.
 * Явный диплинк использует PendingIntent для перехода в конкретное место в приложении.
 * Например, можно разместить явный диплинк в виджете или уведомлении.
 */
inline fun <reified T : Activity> openDeepLink(navController: NavController, navGraph: NavGraph) {
    val pendingIntent: PendingIntent = navController.createDeepLink()
        .setGraph(navGraph)
        .setDestination("feature_activity")
        .setComponentName(T::class.java)
        .createPendingIntent()

    pendingIntent.send()
}
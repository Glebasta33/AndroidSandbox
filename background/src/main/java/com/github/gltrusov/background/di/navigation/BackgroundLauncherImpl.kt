package com.github.gltrusov.background.di.navigation

import android.content.Context
import com.github.gltrusov.background.presentation.BackgroundActivity

internal class BackgroundLauncherImpl : BackgroundLauncher {

    override fun launch(context: Context) {
        BackgroundActivity.createIntent(context)
            .let(context::startActivity)
    }

}
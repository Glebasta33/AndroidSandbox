package com.github.gltrusov.compose.di.navigation

import android.content.Context
import com.github.gltrusov.compose.presentation.ComposeSandboxActivity

internal class ComposeSandboxLauncherImpl : ComposeSandboxLauncher {

    override fun launch(context: Context) {
        ComposeSandboxActivity.createIntent(context)
            .let(context::startActivity)
    }

}
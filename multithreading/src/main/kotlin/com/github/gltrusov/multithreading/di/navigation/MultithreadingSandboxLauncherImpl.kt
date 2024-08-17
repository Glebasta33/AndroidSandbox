package com.github.gltrusov.multithreading.di.navigation

import android.content.Context
import com.github.gltrusov.multithreading.presentation.MultithreadingSandboxActivity

internal class MultithreadingSandboxLauncherImpl : MultithreadingSandboxLauncher {

    override fun launch(context: Context) {
        MultithreadingSandboxActivity.createIntent(context)
            .let(context::startActivity)
    }

}
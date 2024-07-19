package com.github.gltrusov.rxjava.di.navigation

import android.content.Context
import com.github.gltrusov.rxjava.presentation.RxJavaActivity

internal class RxJavaLauncherImpl : RxJavaLauncher {

    override fun launch(context: Context) {
        RxJavaActivity.createIntent(context)
            .let(context::startActivity)
    }

}
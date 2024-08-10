package com.github.gltrusov.fundamentals.di.navigation

import android.content.Context
import com.github.gltrusov.fundamentals.presentation.AndroidOsFundamentalsActivity

internal class AndroidOsFundamentalsLauncherImpl : AndroidOsFundamentalsLauncher {

    override fun launch(context: Context) {
        AndroidOsFundamentalsActivity.createIntent(context)
            .let(context::startActivity)
    }

}
package com.github.gltrusov.views.di.navigation

import android.content.Context
import com.github.gltrusov.views.presentation.ViewsActivity

internal class ViewsLauncherImpl : ViewsLauncher {

    override fun launch(context: Context) {
        ViewsActivity.createIntent(context)
            .let(context::startActivity)
    }

}
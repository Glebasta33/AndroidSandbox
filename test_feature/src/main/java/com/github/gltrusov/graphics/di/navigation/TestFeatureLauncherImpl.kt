package com.github.gltrusov.graphics.di.navigation

import android.content.Context
import com.github.gltrusov.graphics.presentation.TestFeatureActivity

internal class TestFeatureLauncherImpl : TestFeatureLauncher {

    override fun launch(context: Context) {
        TestFeatureActivity.createIntent(context)
            .let(context::startActivity)
    }

}
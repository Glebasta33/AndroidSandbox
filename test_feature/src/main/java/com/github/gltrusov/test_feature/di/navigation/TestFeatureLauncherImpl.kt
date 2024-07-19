package com.github.gltrusov.test_feature.di.navigation

import android.content.Context
import com.github.gltrusov.test_feature.presentation.TestFeatureActivity

internal class TestFeatureLauncherImpl : TestFeatureLauncher {

    override fun launch(context: Context) {
        TestFeatureActivity.createIntent(context)
            .let(context::startActivity)
    }

}
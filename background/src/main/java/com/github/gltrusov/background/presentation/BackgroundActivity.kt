package com.github.gltrusov.background.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.di_framework.core.internalFeatureApi
import com.github.gltrusov.background.R
import com.github.gltrusov.background.di.api.BackgroundApi
import com.github.gltrusov.background.di.internal.BackgroundInternalApi

class BackgroundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_test)
        val internalApi: BackgroundInternalApi = internalFeatureApi<BackgroundApi, BackgroundInternalApi>()
        val textView = findViewById<TextView>(R.id.test_text_view)
        textView.text = internalApi.BackgroundDependencyMock.text
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, BackgroundActivity::class.java)
    }

}
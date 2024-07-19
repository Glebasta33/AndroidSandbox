package com.github.gltrusov.test_feature.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.di_framework.core.internalFeatureApi
import com.github.gltrusov.test_feature.R
import com.github.gltrusov.test_feature.di.api.TestFeatureApi
import com.github.gltrusov.test_feature.di.internal.TestFeatureInternalApi

class TestFeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_test)
        val internalApi: TestFeatureInternalApi = internalFeatureApi<TestFeatureApi, TestFeatureInternalApi>()
        val textView = findViewById<TextView>(R.id.test_text_view)
        textView.text = internalApi.testFeatureDependencyMock.text
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, TestFeatureActivity::class.java)
    }

}
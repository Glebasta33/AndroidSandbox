package com.github.gltrusov.rxjava.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.di_framework.core.internalFeatureApi
import com.github.gltrusov.rxjava.R
import com.github.gltrusov.rxjava.di.api.RxJavaApi
import com.github.gltrusov.rxjava.di.internal.RxJavaInternalApi

class RxJavaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)
        val internalApi: RxJavaInternalApi = internalFeatureApi<RxJavaApi, RxJavaInternalApi>()
        val textView = findViewById<TextView>(R.id.test_text_view)
        textView.text = internalApi.RxJavaDependencyMock.text
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, RxJavaActivity::class.java)
    }

}
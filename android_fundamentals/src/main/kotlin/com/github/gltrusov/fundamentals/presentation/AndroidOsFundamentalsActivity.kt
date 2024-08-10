package com.github.gltrusov.fundamentals.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.di_framework.core.internalFeatureApi
import com.github.gltrusov.fundamentals.R
import com.github.gltrusov.fundamentals.di.api.AndroidOsFundamentalsApi
import com.github.gltrusov.fundamentals.di.internal.AndroidOsFundamentalsInternalApi

class AndroidOsFundamentalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_os_fundamentals)
        val internalApi: AndroidOsFundamentalsInternalApi = internalFeatureApi<AndroidOsFundamentalsApi, AndroidOsFundamentalsInternalApi>()
        val textView = findViewById<TextView>(R.id.test_text_view)
        textView.text = internalApi.androidOsFundamentalsDependencyMock.text
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, AndroidOsFundamentalsActivity::class.java)
    }

}
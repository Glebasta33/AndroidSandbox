package com.github.gltrusov.views.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.di_framework.core.internalFeatureApi
import com.github.gltrusov.views.R
import com.github.gltrusov.views.di.api.ViewsApi
import com.github.gltrusov.views.di.internal.ViewsInternalApi

class ViewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views)
        val internalApi: ViewsInternalApi = internalFeatureApi<ViewsApi, ViewsInternalApi>()
        val textView = findViewById<TextView>(R.id.test_text_view)
        textView.text = internalApi.ViewsDependencyMock.text
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, ViewsActivity::class.java)
    }

}
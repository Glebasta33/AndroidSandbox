package com.github.gltrusov.views.presentation.custom_view.touch_events

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import org.billthefarmer.markdown.MarkdownView

internal class TouchEventsCustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_events_custom_view)

        findViewById<MarkdownView>(R.id.markdown).loadMarkdownFile("file:///android_asset/touch_events_custom_view.md")

    }
}
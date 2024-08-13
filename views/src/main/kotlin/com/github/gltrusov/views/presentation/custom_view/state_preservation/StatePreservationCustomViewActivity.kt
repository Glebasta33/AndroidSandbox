package com.github.gltrusov.views.presentation.custom_view.state_preservation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import org.billthefarmer.markdown.MarkdownView

internal class StatePreservationCustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_preservation_custom_view)

        findViewById<MarkdownView>(R.id.markdown).loadMarkdownFile("file:///android_asset/state_preservation_view.md")

    }
}
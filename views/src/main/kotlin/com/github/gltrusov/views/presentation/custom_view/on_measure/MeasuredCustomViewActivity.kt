package com.github.gltrusov.views.presentation.custom_view.on_measure

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import org.billthefarmer.markdown.MarkdownView

internal class MeasuredCustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measured_custom_view)

        val measuredCustomView = findViewById<MeasuredCustomView>(R.id.measured_custom_view)

        measuredCustomView.setOnClickListener {
            Toast.makeText(this, "CustomView was clicked!", Toast.LENGTH_LONG).show()
        }

        findViewById<MarkdownView>(R.id.markdown).loadMarkdownFile("file:///android_asset/measured_markdown_view.md")

    }
}
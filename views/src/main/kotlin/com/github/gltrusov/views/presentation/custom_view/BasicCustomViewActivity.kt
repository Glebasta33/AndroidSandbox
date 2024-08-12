package com.github.gltrusov.views.presentation.custom_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import org.billthefarmer.markdown.MarkdownView

internal class BasicCustomViewActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_custom_view)

        val basicCustomView = findViewById<BasicCustomView>(R.id.basic_custom_view)

        basicCustomView.setOnClickListener {
            Toast.makeText(this, "CustomView was clicked!", Toast.LENGTH_LONG).show()
        }

        findViewById<MarkdownView>(R.id.markdown).loadMarkdownFile("file:///android_asset/basic_markdown_view.md")

    }
}
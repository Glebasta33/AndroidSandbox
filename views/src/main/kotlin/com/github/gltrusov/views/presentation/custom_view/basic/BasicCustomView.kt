package com.github.gltrusov.views.presentation.custom_view.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.github.gltrusov.views.R
import com.github.gradle_sandbox.Markdown

@Markdown("basic_markdown_view.md")
internal class BasicCustomView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var customText: String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BasicCustomView)
        customText = typedArray.getString(R.styleable.BasicCustomView_customText)
        typedArray.recycle()
    }

    private val bgPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 32f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(width / 2f, height / 2f, /*radius*/200f, bgPaint)
        canvas.drawText(customText ?: "error", width / 3f, height / 2f, textPaint)
    }
}
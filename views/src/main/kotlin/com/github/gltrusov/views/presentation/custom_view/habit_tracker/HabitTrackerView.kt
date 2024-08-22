package com.github.gltrusov.views.presentation.custom_view.habit_tracker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.github.gltrusov.views.R
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal class HabitTrackerView(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val textPaint = TextPaint().apply {
        color = Color.WHITE
        textSize = context.resources.getDimension(R.dimen.gant_task_name_text_size)
    }

    private val checkBoxPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    private val habitHeight = context.resources.getDimension(R.dimen.habit_height)

    private val verticalPadding = context.resources.getDimension(R.dimen.habit_vertical_padding)

    private var habitItems: List<HabitItem>? = null

    fun setItems(habits: List<HabitItem>) {
        if (habits != habitItems) {
            habitItems = habits
        }

        requestLayout()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        with(canvas) {
            drawHabits()
        }
    }

    private fun Canvas.drawHabits() {

        habitItems?.forEachIndexed { index, habitItem ->
            drawText(habitItem.title, verticalPadding, (index + 0.7f) * habitHeight, textPaint)

            habitItem.statistics.forEachIndexed { i, stat ->
                val startX = width / 4

                val statPaint = if (stat.isDone) {
                    checkBoxPaint.apply { color = habitItem.color }
                } else {
                    checkBoxPaint.apply { color = Color.GRAY }
                }

                drawRoundRect(
                    startX.toFloat() + i * habitHeight + 10f,
                    (index) * habitHeight + 10f,
                    startX + i * habitHeight + habitHeight,
                    (index + 1) * habitHeight,
                    8f,
                    8f,
                    statPaint
                )

            }
        }
    }


}

data class HabitItem(
    val title: String,
    val color: Int,
    val statistics: List<Stat>
) {
    data class Stat(
        val date: LocalDate,
        val isDone: Boolean
    )
}
package com.github.gltrusov.views.presentation.custom_view.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.github.gltrusov.views.R
import com.github.gradle_sandbox.Markdown
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Markdown("calendar_markdown_view.md")
internal class CalendarCustomView(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    // region Paint ================================================================================

    private val separatorsPaint = Paint().apply {
        strokeWidth = resources.getDimension(R.dimen.gant_separator_width)
        color = ContextCompat.getColor(context, R.color.grey_300)
    }

    private val hourNamePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.gant_period_name_text_size)
        color = ContextCompat.getColor(context, R.color.grey_500)
    }

    private val eventsShapePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.blue_700)
    }

    private val eventNamePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.gant_task_name_text_size)
        color = Color.WHITE
    }

    private val backgroundPaint = Paint().apply {
        color = Color.DKGRAY
    }
    // endregion  ==================================================================================

    private val hourHeight = resources.getDimensionPixelSize(R.dimen.calendar_hour_height)

    private val eventCornerRadius = resources.getDimension(R.dimen.gant_task_corner_radius)

    private val contentHeight: Int
        get() = hourHeight * visibleHours.size

    private val now = LocalDateTime.now().withMinute(0).withSecond(0)

    private val visibleHours = initHours()

    private var events: List<Event> = emptyList()

    fun setEvents(events: List<Event>) {
        if (events != this.events) {
            this.events = events

            requestLayout()
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when(MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.UNSPECIFIED ->  contentHeight
            MeasureSpec.EXACTLY -> heightSpecSize
            MeasureSpec.AT_MOST -> contentHeight.coerceAtMost(heightSpecSize)
            else -> error("Unreachable")
        }

        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when(MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> 200//TODO Добавить значение для wrap_content (minContentWidth)
            MeasureSpec.EXACTLY -> widthSpecSize
            MeasureSpec.AT_MOST -> 200.coerceAtMost(heightSpecSize)
            else -> error("Unreachable")
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) = with(canvas) {
        drawBackground()
        drawHours()
    }

    private fun Canvas.drawBackground() {
        drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
    }

    private fun Canvas.drawHours() {
        val textX = 20f
        visibleHours.forEachIndexed { index, text ->
            val textY = hourNamePaint.getTextBaselineByCenter(hourHeight * (index + 1f))
            drawText(text, textX, textY, eventNamePaint)

            val textSizeX = eventNamePaint.measureText(text)

            val separatorY = hourHeight * (index + 1f)
            val separatorX = textX + textSizeX + 20f
            drawLine(separatorX, separatorY, width.toFloat(), separatorY, separatorsPaint)
        }
    }

    // ascent - верхняя граница текста, descent - нижняя
    private fun Paint.getTextBaselineByCenter(center: Float) = center - (descent() + ascent()) / 2

    private fun initHours(): List<String> {
        val startTime = now.minusHours(2)
        val endTime = now.plusHours(5)
        var lastTime = startTime
        val formater = DateTimeFormatter.ofPattern(HOUR_PATTERN)
        return mutableListOf<String>().apply {
            while (lastTime <= endTime) {
                add(lastTime.format(formater))
                lastTime = lastTime.plusHours(1)
            }
        }
    }

    companion object {
        private const val HOUR_PATTERN = "HH:mm"
    }
}

data class Event(
    val title: String,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime
)
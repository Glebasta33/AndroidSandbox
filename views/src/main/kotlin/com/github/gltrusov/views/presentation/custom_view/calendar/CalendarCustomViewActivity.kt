package com.github.gltrusov.views.presentation.custom_view.calendar

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import org.billthefarmer.markdown.MarkdownView
import java.time.LocalDateTime

internal class CalendarCustomViewActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_custom_view)

        val calendarCustomView = findViewById<CalendarCustomView>(R.id.calendar_custom_view)

        val now = LocalDateTime.now()
        val events = listOf(
            Event(
                title = "Event 1",
                dateStart = now.withHour(9),
                dateEnd = now.withHour(10)
            ),
            Event(
                title = "Event 2",
                dateStart = now.withHour(11).withMinute(30),
                dateEnd = now.withHour(12)
            )
        )

        calendarCustomView.setEvents(events)

        findViewById<MarkdownView>(R.id.markdown).loadMarkdownFile("file:///android_asset/calendar_markdown_view.md")

    }
}
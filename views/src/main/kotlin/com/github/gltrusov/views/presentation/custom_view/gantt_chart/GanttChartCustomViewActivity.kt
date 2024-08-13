package com.github.gltrusov.views.presentation.custom_view.gantt_chart

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import org.billthefarmer.markdown.MarkdownView
import java.time.LocalDate

internal class GanttChartCustomViewActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gantt_chart_custom_view)

        val ganttChartCustomView = findViewById<GanttChartCustomView>(R.id.gantt_chart_view)

        val now = LocalDate.now()
        val tasks = listOf(
            Task(
                name = "Task 1",
                dateStart = now.minusMonths(1),
                dateEnd = now
            ),
            Task(
                name = "Task 2 long name",
                dateStart = now.minusWeeks(2),
                dateEnd = now.plusWeeks(1)
            ),
            Task(
                name = "Task 3",
                dateStart = now.minusMonths(2),
                dateEnd = now.plusMonths(2)
            ),
            Task(
                name = "Some Task 4",
                dateStart = now.plusWeeks(2),
                dateEnd = now.plusMonths(2).plusWeeks(1)
            ),
            Task(
                name = "Task 5",
                dateStart = now.minusMonths(2).minusWeeks(1),
                dateEnd = now.plusWeeks(1)
            )
        )

        ganttChartCustomView.setTasks(tasks)

        findViewById<MarkdownView>(R.id.markdown).loadMarkdownFile("file:///android_asset/gantt_chart_custom_view.md")

    }
}
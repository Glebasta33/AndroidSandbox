package com.github.gltrusov.views.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import com.github.gltrusov.views.presentation.custom_view.basic.BasicCustomViewActivity
import com.github.gltrusov.views.presentation.custom_view.calendar.CalendarCustomViewActivity
import com.github.gltrusov.views.presentation.custom_view.gantt_chart.GanttChartCustomViewActivity
import com.github.gltrusov.views.presentation.custom_view.on_measure.MeasuredCustomViewActivity
import com.github.gltrusov.views.presentation.custom_view.state_preservation.StatePreservationCustomViewActivity
import com.github.gltrusov.views.presentation.custom_view.touch_events.TouchEventsCustomViewActivity

data class MenuItem(
    val text: String,
    val onClick: () -> Unit
) {
    override fun toString(): String {
        return text
    }
}

internal class ViewsActivity : AppCompatActivity() {

    private val menuItems = listOf(
        MenuItem(
            text = "CustomView (basics example)",
            onClick = { startActivity(Intent(this, BasicCustomViewActivity::class.java)) }
        ),
        MenuItem(
            text = "CustomView (measuring)",
            onClick = { startActivity(Intent(this, MeasuredCustomViewActivity::class.java)) }
        ),
        MenuItem(
            text = "CustomView (state preservation & size changing)",
            onClick = {
                startActivity(
                    Intent(
                        this,
                        StatePreservationCustomViewActivity::class.java
                    )
                )
            }
        ),
        MenuItem(
            text = "CustomView (touch events & redrawing)",
            onClick = { startActivity(Intent(this, TouchEventsCustomViewActivity::class.java)) }
        ),
        MenuItem(
            text = "Gantt Chart",
            onClick = { startActivity(Intent(this, GanttChartCustomViewActivity::class.java)) }
        ),
        MenuItem(
            text = "Calendar",
            onClick = { startActivity(Intent(this, CalendarCustomViewActivity::class.java)) }
        )
    )


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views)

        val listViewMenu = findViewById<ListView>(R.id.list_view)

        val adapter = ArrayAdapter(this, R.layout.menu_list_view_item, menuItems)

        listViewMenu.adapter = adapter

        listViewMenu.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                menuItems[position].onClick.invoke()
            }

    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, ViewsActivity::class.java)
    }

}
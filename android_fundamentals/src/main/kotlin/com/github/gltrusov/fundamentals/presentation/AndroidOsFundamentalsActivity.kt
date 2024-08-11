package com.github.gltrusov.fundamentals.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.fundamentals.BundleActivity
import com.github.gltrusov.fundamentals.ParcelableActivity
import com.github.gltrusov.fundamentals.R
import com.github.gltrusov.fundamentals.SerializationActivity

data class MenuItem(
    val text: String,
    val onClick: () -> Unit
) {
    override fun toString(): String {
        return text
    }
}

internal class AndroidOsFundamentalsActivity : AppCompatActivity() {

    private val menuItems = listOf(
        MenuItem(
            text = "Bundle",
            onClick = { BundleActivity.start(this) }
        ),
        MenuItem(
            text = "Serialization",
            onClick = { startActivity(Intent(this, SerializationActivity::class.java)) }
        ),
        MenuItem(
            text = "Parcelable",
            onClick = { startActivity(Intent(this, ParcelableActivity::class.java)) }
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_os)

        val listViewMenu = findViewById<ListView>(R.id.list_view_menu)

        val adapter = ArrayAdapter(this, R.layout.menu_list_view_item, menuItems)

        listViewMenu.adapter = adapter

        listViewMenu.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                menuItems[position].onClick.invoke()
            }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, AndroidOsFundamentalsActivity::class.java)
    }

}
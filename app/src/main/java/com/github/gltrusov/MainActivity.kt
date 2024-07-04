package com.github.gltrusov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.di_framework.core.featureApi
import com.github.gltrusov.di.entrypoints.api.MainScreenEntryPointsApi
import com.github.gltrusov.ui.DefaultEntryPointsAdapter

@OptIn(OnlyForMainScreen::class)
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView: RecyclerView = findViewById(R.id.main_screen_entry_points_recycler_view)
        val adapter = DefaultEntryPointsAdapter<EntryPoint.ActivityEntryPoint>(
            onItemClick = { entryPoint ->
                entryPoint.launcher.launch(context = this)
            }
        )
        recyclerView.adapter = adapter
        val mainScreenEntryPoints = featureApi<MainScreenEntryPointsApi>()
            .mainScreenEntryPointsMap
            .values
            .toList()
        adapter.submitList(mainScreenEntryPoints)
    }
}
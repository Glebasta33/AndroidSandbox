package com.github.gltrusov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.github.gltrusov.ui.theme.AndroidSandboxTheme

/**
 * ## Navigation.
 * Навигаяция в Android состоит из 3-х ключевых концепций:
 * - Host - UI элемент, который содержит текущую точку назначения навигации (destination).
 * В процессе навигации destinations меняются внутри Host.
 * - Graph - структура данных определяющая места назначения и их связь между собой.
 * - Controller - центральный координатор, управляющий навигацией между точками назначения.
 * Имеет методы для навигации, обработки диплинков, управления back stack`ом и т.д.
 *
 * ! Эти 3 концепции используются как при навигации в View, так и в Compose.
 *
 * ## Create a navigation controller.
 * Controller содержит граф и предоставляет методы навигации по графу. Класс NavController - центральный API для навигации.
 * Чтобы создать NavController:
 * Compose: val navController = rememberNavController()
 * Views:
 * - Fragment.findNavController()
 * - View.findNavController()
 * - Activity.findNavController(viewId: Int)
 *
 * ## Design your navigation graph.
 * Есть 3 отсновныт типа destinations:
 * - Hosted - то, что заполняет Host.
 * - Dialog - алерты.
 * - Activity - отдельная активность (за пределами текущего графа).
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        /**
         * Более устарвеший подход создания графа - через XML.
         * Можно создавать, используя Kotlin DSL, что почти как в Compose:
         */
        val navGraph: NavGraph = navController.createGraph(
            startDestination = "root_catalog"
        ) {
            fragment<RootCatalogFragment>("root_catalog") {
                label = "Root catalog"
            }
            fragment<CustomViewFragment>("custom_view") {
                label = "Custom view"
            }
            fragment<RecyclerViewFragment>("recycler_view") {
                label = "Recycler view"
            }
        }
        navController.setGraph(navGraph, null)
    }
}

class RootCatalogFragment : Fragment() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                viewLifecycleOwner
            )
        )
        setContent {
            AndroidSandboxTheme {
                LazyColumn {
                    items(listOf("custom_view", "recycler_view")) { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(item)
                                }
                        )
                    }
                }
            }
        }
    }
}


class CustomViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                viewLifecycleOwner
            )
        )
        setContent {
            AndroidSandboxTheme {
                Text("CustomView")
            }
        }
    }
}

class RecyclerViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                viewLifecycleOwner
            )
        )
        setContent {
            AndroidSandboxTheme {
                Text("RecyclerView")
            }
        }
    }
}
package com.github.gltrusov.navigation_sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import com.github.gltrusov.R
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
class NavigationDraftActivity : AppCompatActivity(R.layout.activity_main_legacy) {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        setupActionBar()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        /**
         * Более устарвеший подход создания графа - через XML.
         * Можно создавать, используя Kotlin DSL, что почти как в Compose:
         */
        val navGraph: NavGraph = navController.createGraph(
            startDestination = ScreenSample.RootCatalog.route
        ) {
            //TODO: Подумать как реализовать создание графа динамически (на основе списка или через рефлексию).
            fragment<RootCatalogFragment>(ScreenSample.RootCatalog.route)
            fragment<CustomViewFragment>(ScreenSample.CustomView.route)
            fragment<RecyclerViewFragment>(ScreenSample.RecyclerView.route)
            activity(ScreenSample.ExampleCompose.route) {
                activityClass = ExampleComposeActivity::class
            }
            /**
             * Вложенный граф создаётся тем же самым методом, что и в Compose - navigation().
             */
            navigation(startDestination = ScreenSample.Solid.route, route = ScreenSample.SolidRoot.route) {
                fragment<SolidFragment>(ScreenSample.Solid.route)
                fragment<SingleResponsibilityFragment>(ScreenSample.SingleResponsibility.route)
                fragment<OpenClosedFragment>(ScreenSample.OpenClosed.route)
            }
        }
        navController.setGraph(navGraph, null)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (navController.currentBackStack.value.isNotEmpty()) {
                navController.popBackStack()
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class RootCatalogFragment : Fragment() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         *  Navigation - точка входа для операций навигации.
         *  Класс Navigation способен найти релевантный NavController из множества мест в приложении.
         */
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    /**
     * Хорошо бы где-то хранить список экранов в едином месте.
     */
    val screens = listOf(
        ScreenSample.CustomView,
        ScreenSample.RecyclerView,
        ScreenSample.ExampleCompose,
        ScreenSample.SolidRoot,
        ScreenSample.FeatureFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            AndroidSandboxTheme {
                LazyColumn {
                    items(screens) { item ->
                        Text(
                            text = item.javaClass.simpleName,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(item.route)
                                }
                        )
                    }
                }
            }
        }
    }
}

// example region:
class CustomViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
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
        setContent {
            AndroidSandboxTheme {
                Text("RecyclerView")
            }
        }
    }
}

//Фрагменты для вложенного графа:
class SolidFragment : Fragment() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    val screens = listOf(
        ScreenSample.SingleResponsibility,
        ScreenSample.OpenClosed
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            AndroidSandboxTheme {
                LazyColumn {
                    items(screens) { item ->
                        Text(
                            text = item.javaClass.simpleName,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(item.route)
                                }
                        )
                    }
                }
            }
        }
    }
}

class SingleResponsibilityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            AndroidSandboxTheme {
                Text("SingleResponsibility")
            }
        }
    }
}

class OpenClosedFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var navGraph: NavGraph
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navGraph = navController.graph
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            AndroidSandboxTheme {
                Text(
                    "OpenClosed",
                    modifier = Modifier.clickable {
                        navController.navigate(ScreenSample.FeatureActivity.route)
//                        openDeepLink<FeatureActivity>(navController, navGraph)
                    }
                )
            }
        }
    }
}
// region end.

sealed class ScreenSample(
    val route: String
) {
    data object RootCatalog : ScreenSample("root_catalog")
    data object CustomView : ScreenSample("custom_view")
    data object RecyclerView : ScreenSample("recycler_view")
    data object ExampleCompose : ScreenSample("example_activity")
    data object FeatureActivity : ScreenSample("feature_activity")
    data object FeatureFragment : ScreenSample("feature_fragment")
    data object SolidRoot : ScreenSample("solid_root")
    data object Solid : ScreenSample("solid")
    data object SingleResponsibility : ScreenSample("single_responsibility")
    data object OpenClosed : ScreenSample("open_closed")
}
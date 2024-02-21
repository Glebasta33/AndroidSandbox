package com.github.gltrusov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.navigation.Navigation
import com.github.gltrusov.navigation.Screen
import com.github.gltrusov.ui.theme.AndroidSandboxTheme

class RootFragment : Fragment() {
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
        Screen.ViewModel
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
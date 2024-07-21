package com.github.gltrusov.rxjava.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gltrusov.rxjava.presentation.custom_observable.CustomObservableActivity
import com.github.gltrusov.rxjava.presentation.network_call.NetworkCallActivity

class RxJavaActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                EntryButton("Custom Observable") {
                    CustomObservableActivity.start(context)
                }
                EntryButton("Network call in Android") {
                    NetworkCallActivity.start(context)
                }
            }
        }
    }


    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, RxJavaActivity::class.java)
    }

}

@Composable
private fun EntryButton(
    title: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text = title, modifier = Modifier.padding(4.dp))
    }
}
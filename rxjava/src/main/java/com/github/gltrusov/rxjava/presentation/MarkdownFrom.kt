package com.github.gltrusov.rxjava.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mukesh.MarkDown
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun MarkdownFrom(
    fileName: String,
    modifier: Modifier
) {
    val context = LocalContext.current
    val mdFile = remember {
        val assetManager = context.assets
        val fileInputStream = assetManager.open(fileName)
        BufferedReader(InputStreamReader(fileInputStream)).readText()
    }

    MarkDown(text = mdFile, modifier = modifier
        .fillMaxWidth()
    )
}
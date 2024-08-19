package com.github.gltrusov.core.ui.compose

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mukesh.MarkDown
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun ColumnScope.MarkdownFrom(
    fileName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mdFile = remember {
        val assetManager = context.assets
        val fileInputStream = assetManager.open(fileName)
        BufferedReader(InputStreamReader(fileInputStream)).readText()
    }

    MarkDown(text = mdFile, modifier = modifier
        .weight(1f)
        .fillMaxWidth()
        .fillMaxWidth()
    )
}
package com.github.gltrusov.compose.samples.terminal.data

import com.google.gson.annotations.SerializedName

data class Bar(
    @SerializedName("o") val open: Float,
    @SerializedName("c") val close: Float,
    @SerializedName("l") val lowest: Float,
    @SerializedName("h") val highest: Float,
    @SerializedName("t") val time: Long
)

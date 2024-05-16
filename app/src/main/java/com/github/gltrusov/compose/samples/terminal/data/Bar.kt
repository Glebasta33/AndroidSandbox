package com.github.gltrusov.compose.samples.terminal.data

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date

@Immutable
@Parcelize
data class Bar(
    @SerializedName("o") val open: Float,
    @SerializedName("c") val close: Float,
    @SerializedName("l") val lowest: Float,
    @SerializedName("h") val highest: Float,
    @SerializedName("t") val time: Long
): Parcelable {
    val calendar: Calendar
        get() = Calendar.getInstance().apply {
            time = Date(this@Bar.time)
        }
}

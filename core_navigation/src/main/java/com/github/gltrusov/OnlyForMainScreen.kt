package com.github.gltrusov

import android.annotation.SuppressLint

@SuppressLint("ExperimentalAnnotationRetention")
@RequiresOptIn(message = "Данная сущность предназначена для использования только на главном экране.")
annotation class OnlyForMainScreen

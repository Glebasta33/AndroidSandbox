package com.github.gltrusov

import androidx.annotation.DrawableRes

sealed interface EntryPoint {

    val name: String
    val description: String?
    val iconResId: Int?

    @OnlyForMainScreen
    data class ActivityEntryPoint(
        override val name: String,
        override val description: String?,
        @DrawableRes
        override val iconResId: Int? = null,
        val launcher: ActivityLauncher
    ) : EntryPoint

    data class FragmentEntryPoint(
        override val name: String,
        override val description: String?,
        @DrawableRes
        override val iconResId: Int? = null,
        //TODO Заменить на какую-то другую сущность.
        // В ней должна быть только информация о фрагменте, на который мы хотим перейти.
        // За способ перехода должна отвечать другая сущность по типу NavController.
        // В этом NavController должна быть возможность перейти на фрагмент через add или через replace.
        // А также гибкая кастомизация анимации перехода.
        val fragmentLauncherProvider: () -> FragmentLauncher
    ) : EntryPoint
}


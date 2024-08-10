package com.github.gltrusov.fundamentals.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.fundamentals.di.api.AndroidOsFundamentalsApi

@ProvidedBy(AndroidOsFundamentalsApi::class)
interface AndroidOsFundamentalsLauncher : ActivityLauncher
package com.github.gltrusov.multithreading.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.multithreading.di.api.MultithreadingSandboxApi

@ProvidedBy(MultithreadingSandboxApi::class)
interface MultithreadingSandboxLauncher : ActivityLauncher
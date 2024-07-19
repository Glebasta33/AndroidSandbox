package com.github.gltrusov.rxjava.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.rxjava.di.api.RxJavaApi

@ProvidedBy(RxJavaApi::class)
interface RxJavaLauncher : ActivityLauncher
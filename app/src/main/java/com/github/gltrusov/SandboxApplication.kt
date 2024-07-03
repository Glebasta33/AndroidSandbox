package com.github.gltrusov

import android.app.Application
import com.github.gltrusov.startup.DiStartup

class SandboxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DiStartup().create(this)
    }
}
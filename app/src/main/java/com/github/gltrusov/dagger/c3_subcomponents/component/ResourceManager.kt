package com.github.gltrusov.dagger.c3_subcomponents.component

import android.content.Context
import dagger.Reusable
import javax.inject.Inject

/**
 * Stateless dependency
 */
@Reusable
class ResourceManager @Inject constructor(private val context: Context)
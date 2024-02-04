package com.github.gltrusov.dagger.c3_subcomponents.component

import javax.inject.Inject

/**
 * Statefull dependency
 */
@AppScope
class Counter @Inject constructor() {

    var count = 0
}
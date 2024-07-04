package com.github.di_framework.annotation

import javax.inject.Scope

/**
 * Скоуп для фичевых зависимостей.
 * В случае применения зависимость будет сохраняться в памяти до вызова [FeatureHolder.releaseFeature].
 */
@Scope
annotation class PerFeature
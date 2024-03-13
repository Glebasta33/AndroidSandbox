package com.github.di_framework.meta

/** Маркерный интерфейс для API, предоставляемого модулем */
interface Api

interface GlobalApi : Api

interface FeatureApi : Api

interface ReleasableApi: Api
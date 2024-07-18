package com.github.gradle_sandbox

// Annotation processing - https://habr.com/ru/companies/e-legion/articles/206208/
@Retention(AnnotationRetention.SOURCE)
annotation class Markdown(val markdownName: String)

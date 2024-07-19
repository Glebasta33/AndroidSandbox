pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "AndroidSandbox"
include(":app")
include(":di_framework")
include(":core_navigation")
include(":core_ui")

include(":test_feature")
include(":compose")
include(":background")
include(":gradle_sandbox")
include(":rxjava")

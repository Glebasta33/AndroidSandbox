pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
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

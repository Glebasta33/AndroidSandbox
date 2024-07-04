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
    }
}

rootProject.name = "AndroidSandbox"
include(":app")
include(":core")
include(":api")
include(":feature_sample")
include(":di_framework")
include(":core_navigation")
include(":core_ui")
include(":test_feature")

pluginManagement {
    repositories {
        maven { url = uri("https://maven.myket.ir") }
        maven { url = uri("https://gradle.jamko.ir") }
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://maven.myket.ir") }
        maven { url = uri("https://gradle.jamko.ir") }
    }
}

rootProject.name = "Simple Note"
include(":app")

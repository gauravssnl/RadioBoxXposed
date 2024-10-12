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
        maven("https://raw.githubusercontent.com/rovo89/XposedBridge/refs/heads/gh-pages/")
    }
}

rootProject.name = "RadioBoxPro"
include(":app")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Track Hub"
include(":app")
include(":validation")
include(":core-ui")
include(":network:core-network")
include(":network:feat-network")
include(":navigation:core-navigation")
include(":navigation:feat-navigation")
include(":auth:core-auth")
include(":auth:feat-auth")
include(":hub:core-hub")
include(":hub:feat-hub")
include(":more:core-more")
include(":more:feat-more")

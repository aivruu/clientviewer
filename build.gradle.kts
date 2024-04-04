plugins {
    java
    alias(libs.plugins.indra)
    alias(libs.plugins.indra.checkstyle)
    alias(libs.plugins.spotless)
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "net.kyori.indra")
    apply(plugin = "net.kyori.indra.checkstyle")
    apply(plugin = "com.diffplug.spotless")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.triumphteam.dev/snapshots/")
    }
    
    indra {
        javaVersions {
            target(17)
            minimumToolchain(17)
        }
        checkstyle("10.13.0")
    }

    spotless {
        java {
            licenseHeaderFile("$rootDir/header.txt")
            removeUnusedImports()
            trimTrailingWhitespace()
            indentWithSpaces(2)
        }
        kotlinGradle {
            trimTrailingWhitespace()
            indentWithSpaces(2)
        }
    }

    dependencies {
        checkstyle("ca.stellardrift:stylecheck:0.2.1")
    }

    tasks {
        compileJava {
            dependsOn("spotlessApply")
            dependsOn("checkstyleMain")
            options.compilerArgs.add("-parameters")
        }
    }
}

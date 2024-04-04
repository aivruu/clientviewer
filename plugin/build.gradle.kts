plugins {
    alias(libs.plugins.shadow)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("Plugin")
    }
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }
}

dependencies {
    implementation(project(":clientviewer-api"))

    compileOnly(libs.paper)
    compileOnly(libs.command)
}

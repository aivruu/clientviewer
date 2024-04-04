@file:Suppress("UnstableApiUsage")

rootProject.name = "clientviewer"

/*
 * Inclusion settings for common subprojects.
 */
arrayOf("api", "plugin").forEach {
    val project = ":clientviewer-$it"
    include(project)
    project(project).projectDir = file(it)
}

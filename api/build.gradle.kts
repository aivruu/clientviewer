plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly(libs.minimessage)
    compileOnly(libs.configurate)
}

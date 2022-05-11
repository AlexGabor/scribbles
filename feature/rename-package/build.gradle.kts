plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":ui:theme"))
    implementation(project(":library:directory-browser"))
    implementation(project(":library:project-info"))
    implementation(kotlin("stdlib"))
    implementation(compose.desktop.currentOs)
}


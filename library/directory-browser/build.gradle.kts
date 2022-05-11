plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":ui:theme"))
    implementation(kotlin("stdlib"))
    implementation(compose.desktop.currentOs)
}
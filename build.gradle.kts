plugins {
    kotlin("jvm") version "1.9.0"  // 2.2.21
}

group = "si.um.feri.lpm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:4.5.2")
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:4.9.3")   // Lets-Plot Kotlin API
    implementation(project(":EARS"))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}
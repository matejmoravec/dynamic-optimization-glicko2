plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "dynamic-optimization-glicko2"
include(":EARS")
project(":EARS").projectDir = file("D:\\EARS")
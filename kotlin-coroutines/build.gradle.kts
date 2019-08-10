import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "com.github.nowakprojects"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

object Versions{
    val jUnit = "5.3.2"
    val assertj = "3.12.2"
    val assertk = "0.17"
    val spek = "2.0.5"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-core","1.2.2")

    testCompile("org.jetbrains.kotlin:kotlin-test")
    testCompile("org.junit.jupiter:junit-jupiter-api:${Versions.jUnit}")
    testCompile("org.junit.jupiter:junit-jupiter-params:${Versions.jUnit}")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit}")
    testCompile("org.assertj:assertj-core:${Versions.assertj}")
    testCompile("org.spekframework.spek2:spek-dsl-jvm:${Versions.spek}")
    testRuntime("org.spekframework.spek2:spek-runner-junit5:${Versions.spek}")
    testCompile("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "12"
}

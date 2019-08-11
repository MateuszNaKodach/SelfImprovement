import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "com.github.nowakprojects"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://dl.bintray.com/spekframework/spek/")
}

object Versions{
    const val jUnit = "5.3.2"
    const val assertj = "3.12.2"
    const val assertk = "0.17"
    const val spek = "2.0.6"
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

kotlin { // type is KotlinJvmProjectExtension
    experimental.coroutines = Coroutines.ENABLE
}
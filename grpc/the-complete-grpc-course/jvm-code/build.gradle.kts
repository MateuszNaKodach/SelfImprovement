import com.google.protobuf.gradle.*
import org.gradle.api.internal.HasConvention
import org.gradle.kotlin.dsl.provider.gradleKotlinDslOf

plugins {
    java
    kotlin("jvm") version "1.4.10"
    id("com.google.protobuf") version "0.8.13"
}

// This extension is not auto generated when we apply the plugin using
// apply(plugin = "com.google.protobuf")
val Project.protobuf: ProtobufConvention get() =
    this.convention.getPlugin(ProtobufConvention::class)

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.protobuf", "protobuf-java", "3.13.0")
    implementation("com.google.protobuf", "protobuf-java-util", "3.13.0")
    implementation("javax.annotation", "javax.annotation-api", "1.3.2")

    testImplementation("junit", "junit", "4.12")

    implementation("io.grpc", "grpc-all", "1.33.0")
}

val grpcCompile by configurations.creating

the<JavaPluginConvention>().sourceSets {

    val grpc by creating {
        compileClasspath += grpcCompile
    }

    "test"{
        compileClasspath += grpc.output
        runtimeClasspath += grpc.output
    }
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java")
    java.srcDir("build/generated/source/proto/main/grpc")
    java.srcDir("build/generated/source/proto/main/java")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.1"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.25.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("grpc").forEach { task ->
            task.plugins {
                id("grpc") {
                    outputSubDir = "grpc_output"
                }
            }
            task.generateDescriptorSet = true
        }
    }
}

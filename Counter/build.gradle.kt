import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"

}

group = "atheera"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-test-junit:1.5.21")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.miglayout:miglayout:3.7.4")
    api("com.google.api.client:google-api-client-json:1.2.3-alpha")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "counter.CounterKT"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
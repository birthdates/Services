plugins {
    kotlin("jvm") version "2.0.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
}

group = "com.birthdates"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks {
    shadowJar {
        // Remove -all from JAR file
        archiveClassifier.set("")
    }

    jar {
        enabled = false
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks.shadowJar.get())
        }
    }

    repositories {
        mavenLocal()
    }
}
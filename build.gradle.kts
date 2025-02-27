plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("maven-publish")
}

group = "com.github.xTecciz"
version = "0.0.1"

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.kotlinGradlePlugin)
    compileOnly(libs.androidGradlePlugin)
}

gradlePlugin {
    plugins.create(project.name) {
        id = "org.tck.gradleconfig"
        implementationClass = "org.tck.gradle.GradleSetupPlugin"
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "com.github.xTecciz"
            artifactId = "somegradleplugins"
            version = "0.0.1"
        }
    }
}
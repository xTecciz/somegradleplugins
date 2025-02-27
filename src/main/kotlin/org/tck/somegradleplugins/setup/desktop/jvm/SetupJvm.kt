package org.tck.gradle.setup.desktop.jvm

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.setupJvm() {
    extensions.configure<KotlinMultiplatformExtension> {
        jvm("desktop")
    }
}

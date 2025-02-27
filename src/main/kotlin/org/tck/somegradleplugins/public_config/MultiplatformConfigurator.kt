package org.tck.gradle.public_config

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun interface MultiplatformConfigurator {
    operator fun KotlinMultiplatformExtension.invoke()
}
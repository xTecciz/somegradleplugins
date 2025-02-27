package org.tck.gradle.utils

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

internal enum class EnabledTarget {
    ANDROID,
    JVM,
}

internal fun Project.isMultiplatformTargetEnabled(target: EnabledTarget): Boolean =
    multiplatformExtension.targets.any {
        when (it.platformType) {
            KotlinPlatformType.androidJvm -> target == EnabledTarget.ANDROID
            KotlinPlatformType.jvm -> target == EnabledTarget.JVM
            KotlinPlatformType.common,
            KotlinPlatformType.js,
            KotlinPlatformType.native,
            KotlinPlatformType.wasm -> false
        }
    }

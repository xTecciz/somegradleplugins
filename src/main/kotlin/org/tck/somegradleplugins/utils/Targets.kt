package org.tck.somegradleplugins.utils

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family

internal enum class EnabledTarget {
    ANDROID,
    JVM,
    IOS,
    MACOS
}

internal fun Project.isMultiplatformTargetEnabled(target: EnabledTarget): Boolean =
    multiplatformExtension.targets.any {
        when (it.platformType) {
            KotlinPlatformType.androidJvm -> target == EnabledTarget.ANDROID
            KotlinPlatformType.jvm -> target == EnabledTarget.JVM
            KotlinPlatformType.native -> when ((it as KotlinNativeTarget).konanTarget.family) {
                Family.IOS -> target == EnabledTarget.IOS
                Family.OSX -> target == EnabledTarget.MACOS
                else -> false
            }
            KotlinPlatformType.common,
            KotlinPlatformType.js,
            KotlinPlatformType.wasm -> false
        }
    }


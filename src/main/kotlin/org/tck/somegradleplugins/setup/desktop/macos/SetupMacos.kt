package org.tck.somegradleplugins.setup.desktop.macos

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.tck.somegradleplugins.setup.requireDefaults
import org.tck.somegradleplugins.public_config.AppleConfig
import org.tck.somegradleplugins.utils.multiplatformExtension

fun Project.setupMacOs() {
    setupMacOsTargets(requireDefaults())
}

private fun Project.setupMacOsTargets(config: AppleConfig) {
    multiplatformExtension.apply {
        listOf(
            macosX64(),
            macosArm64()
        ).forEach { macTarget ->
            macTarget.binaries.framework {
                baseName = config.frameworkName
                isStatic = config.isStatic
            }
        }
    }
}

internal fun Project.setupMacosCommon(config: AppleConfig) {
    multiplatformExtension.apply {
        listOf(
            macosX64(),
            macosArm64()
        ).forEach { macTarget ->
            macTarget.binaries.framework {
                baseName = config.frameworkName
                isStatic = config.isStatic
            }

            macTarget.compilations.configureEach {
                compileTaskProvider.configure {
                    compilerOptions {
                        freeCompilerArgs.addAll(
                            "-Xobjc-generics",
                            "-Xenable-apple-framework-compatibility"
                        )
                    }
                }
            }
        }
    }
}

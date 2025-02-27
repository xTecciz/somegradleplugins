package org.tck.somegradleplugins.setup.mobile.ios

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.tck.somegradleplugins.public_config.AppleConfig
import org.tck.somegradleplugins.setup.requireDefaults
import org.tck.somegradleplugins.utils.multiplatformExtension

fun Project.setupIos() {
    setupIosCommon(requireDefaults())
}

internal fun Project.setupIosCommon(config: AppleConfig) {
    multiplatformExtension.apply {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = config.frameworkName
                isStatic = config.isStatic
            }

            iosTarget.compilations.configureEach {
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

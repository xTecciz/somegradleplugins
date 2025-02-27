package org.tck.somegradleplugins.setup

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.tck.somegradleplugins.setup.mobile.android.setupAndroidCommon
import org.tck.somegradleplugins.source_sets.DefaultMultiplatformSourceSets
import org.tck.somegradleplugins.source_sets.MultiplatformSourceSets
import org.tck.somegradleplugins.public_config.MultiplatformConfigurator
import org.tck.somegradleplugins.utils.EnabledTarget
import org.tck.somegradleplugins.utils.configureExtension
import org.tck.somegradleplugins.utils.disableCompilationsOfNeeded
import org.tck.somegradleplugins.utils.isMultiplatformTargetEnabled
import org.tck.somegradleplugins.utils.multiplatformExtension

fun Project.setupMultiplatform(
    targets: MultiplatformConfigurator = requireDefaults(),
) {
    multiplatformExtension.apply {
        with(targets) { invoke() }

        setupSourceSets {
            common.main.dependencies {
                implementation(kotlin("stdlib"))
            }

            common.test.dependencies {
                implementation(kotlin("test"))
            }
        }

        configureJvmAndAndroidTargets()

        disableCompilationsOfNeeded()
    }

    if (isMultiplatformTargetEnabled(EnabledTarget.ANDROID)) {
        setupAndroidCommon(requireDefaults())
    }

    configureExtension<JavaPluginExtension> {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
}

private fun KotlinMultiplatformExtension.configureJvmAndAndroidTargets() {
    this.targets.configureEach {
        when (this) {
            is KotlinAndroidTarget ->
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }

            is KotlinJvmTarget ->
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
        }

        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.setupSourceSets(block: MultiplatformSourceSets.() -> Unit) {
    DefaultMultiplatformSourceSets(targets, sourceSets).block()
}

package org.tck.somegradleplugins.setup.mobile.android

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.tck.somegradleplugins.setup.requireDefaults
import org.tck.somegradleplugins.public_config.AndroidConfig
import org.tck.somegradleplugins.utils.configureNamespace

fun Project.setupAndroidLibrary() {
    setupAndroid(requireDefaults())
}

fun Project.setupAndroidApp(
    applicationId: String,
    versionCode: Int,
    versionName: String,
) {
    setupAndroid(requireDefaults())

    extensions.configure<BaseAppModuleExtension> {
        defaultConfig {
            this.applicationId = applicationId
            this.versionCode = versionCode
            this.versionName = versionName
        }
    }
}

private fun Project.setupAndroid(config: AndroidConfig) {
    setupAndroidCommon(config)

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

internal fun Project.setupAndroidCommon(config: AndroidConfig) {
    extensions.configure<BaseExtension> {
        configureNamespace(this, config.rootProjectName)

        compileSdkVersion(config.compileSdkVersion)

        defaultConfig {
            minSdk = config.minSdkVersion
            targetSdk = config.targetSdkVersion
        }

        compileOptions {
            sourceCompatibility(JavaVersion.VERSION_17)
            targetCompatibility(JavaVersion.VERSION_17)
        }
    }
}
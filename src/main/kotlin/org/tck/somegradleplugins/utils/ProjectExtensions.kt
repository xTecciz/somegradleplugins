package org.tck.somegradleplugins.utils

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

fun Project.configureNamespace(
    extension: BaseExtension,
    rootProjectName: String
) = extension.apply {
    val moduleName = path.split(":")
        .drop(1)
        .joinToString(".")
        .replace("-", "_")

    namespace = moduleName.takeIf { it.isNotEmpty() }
        ?.let { "$rootProjectName.$it" }
        ?: rootProjectName
}

internal val Project.multiplatformExtension: KotlinMultiplatformExtension
    get() = kotlinExtension as KotlinMultiplatformExtension

internal fun Project.checkIsRootProject() {
    check(rootProject == this) { "Must be called on a root project" }
}

internal inline fun <reified T : Any> Project.hasExtension(): Boolean =
    try {
        extensions.findByType(T::class) != null
    } catch (e: NoClassDefFoundError) {
        false
    }

internal inline fun <reified T : Any> Project.configureExtension(crossinline configure: T.() -> Unit) {
    if (hasExtension<T>()) {
        extensions.configure<T> {
            configure()
        }
    }
}

package org.tck.somegradleplugins.source_sets

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

data class SourceSetBundle(
    val main: KotlinSourceSet,
    val test: KotlinSourceSet,
)

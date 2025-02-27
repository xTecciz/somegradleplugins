package org.tck.gradle.source_sets

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

data class SourceSetBundle(
    val main: KotlinSourceSet,
    val test: KotlinSourceSet,
)

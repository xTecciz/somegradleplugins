package org.tck.gradle.setup.web.wasm

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.setupWasm() {
    setupWasmCommon()
}

@OptIn(ExperimentalWasmDsl::class)
private fun Project.setupWasmCommon() {
    extensions.configure<KotlinMultiplatformExtension> {
        wasmJs {
            browser()
            binaries.executable()
        }
    }
}

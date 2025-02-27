package org.tck.gradle.target_compat

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.androidCompat() {
    androidTarget()
}

fun KotlinMultiplatformExtension.jvmCompat(name: String? = DEFAULT_TARGET_NAME) {
    enableTarget(name = name, enableDefault = { jvm() }, enableNamed = { jvm(it) })
}

fun KotlinMultiplatformExtension.jsCompat(name: String? = DEFAULT_TARGET_NAME) {
    enableTarget(name = name, enableDefault = { js { browser() } }, enableNamed = { js(it) { browser() } })
}

@OptIn(ExperimentalWasmDsl::class)
fun KotlinMultiplatformExtension.wasmCompat(name: String? = DEFAULT_TARGET_NAME) {
    enableTarget(name = name, enableDefault = { wasmJs { browser() } }, enableNamed = { wasmJs(it) { browser() } })
}

fun KotlinMultiplatformExtension.iosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
    simulatorArm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(name = x64, enableDefault = { iosX64() }, enableNamed = { iosX64(it) })
    enableTarget(name = arm64, enableDefault = { iosArm64() }, enableNamed = { iosArm64(it) })
    enableTarget(name = simulatorArm64, enableDefault = { iosSimulatorArm64() }, enableNamed = { iosSimulatorArm64(it) })
}

fun KotlinMultiplatformExtension.watchosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm32: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
    simulatorArm64: String? = DEFAULT_TARGET_NAME,
    deviceArm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(name = x64, enableDefault = { watchosX64() }, enableNamed = { watchosX64(it) })
    enableTarget(name = arm32, enableDefault = { watchosArm32() }, enableNamed = { watchosArm32(it) })
    enableTarget(name = arm64, enableDefault = { watchosArm64() }, enableNamed = { watchosArm64(it) })
    enableTarget(name = simulatorArm64, enableDefault = { watchosSimulatorArm64() }, enableNamed = { watchosSimulatorArm64(it) })
    enableTarget(name = deviceArm64, enableDefault = { watchosDeviceArm64() }, enableNamed = { watchosDeviceArm64(it) })
}

fun KotlinMultiplatformExtension.tvosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
    simulatorArm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(name = x64, enableDefault = { tvosX64() }, enableNamed = { tvosX64(it) })
    enableTarget(name = arm64, enableDefault = { tvosArm64() }, enableNamed = { tvosArm64(it) })
    enableTarget(name = simulatorArm64, enableDefault = { tvosSimulatorArm64() }, enableNamed = { tvosSimulatorArm64(it) })
}

fun KotlinMultiplatformExtension.macosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(name = x64, enableDefault = { macosX64() }, enableNamed = { macosX64(it) })
    enableTarget(name = arm64, enableDefault = { macosArm64() }, enableNamed = { macosArm64(it) })
}

// TODO
//fun KotlinMultiplatformExtension.desktopCompat(
//    macosX64: String? = DEFAULT_TARGET_NAME,
//    macosArm64: String? = DEFAULT_TARGET_NAME,
//    linuxX64: String? = DEFAULT_TARGET_NAME,
//    linuxArm64: String? = DEFAULT_TARGET_NAME,
//    mingw: String? = DEFAULT_TARGET_NAME,
//) {
//    macosCompat(macosX64, macosArm64)
//    linuxCompat(linuxX64, linuxArm64)
//    mingwCompat(mingw)
//}
//
//fun KotlinMultiplatformExtension.linuxCompat(
//    x64: String? = DEFAULT_TARGET_NAME,
//    arm64: String? = DEFAULT_TARGET_NAME,
//) {
//    enableTarget(name = x64, enableDefault = { linuxX64() }, enableNamed = { linuxX64(it) })
//    enableTarget(name = arm64, enableDefault = { linuxArm64() }, enableNamed = { linuxArm64(it) })
//}
//
//fun KotlinMultiplatformExtension.mingwCompat(name: String? = DEFAULT_TARGET_NAME) {
//    enableTarget(name = name, enableDefault = { mingwX64() }, enableNamed = { mingwX64(it) })
//}
//
//fun KotlinMultiplatformExtension.androidNativeCompat(
//    x64: String? = DEFAULT_TARGET_NAME,
//    x86: String? = DEFAULT_TARGET_NAME,
//    arm32: String? = DEFAULT_TARGET_NAME,
//    arm64: String? = DEFAULT_TARGET_NAME,
//) {
//    enableTarget(name = x64, enableDefault = { androidNativeX64() }, enableNamed = { androidNativeX64(it) })
//    enableTarget(name = x86, enableDefault = { androidNativeX86() }, enableNamed = { androidNativeX86(it) })
//    enableTarget(name = arm32, enableDefault = { androidNativeArm32() }, enableNamed = { androidNativeArm32(it) })
//    enableTarget(name = arm64, enableDefault = { androidNativeArm64() }, enableNamed = { androidNativeArm64(it) })
//}

private fun KotlinMultiplatformExtension.enableTarget(
    name: String?,
    enableDefault: KotlinMultiplatformExtension.() -> Unit,
    enableNamed: KotlinMultiplatformExtension.(String) -> Unit,
) {
    if (name != null) {
        if (name == DEFAULT_TARGET_NAME) {
            enableDefault()
        } else {
            enableNamed(name)
        }
    }
}

private const val DEFAULT_TARGET_NAME = "DEFAULT_TARGET_NAME"

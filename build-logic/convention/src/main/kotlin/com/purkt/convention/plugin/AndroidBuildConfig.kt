package com.purkt.convention.plugin

object AndroidBuildConfig {
    const val APPLICATION_ID = "com.purkt.mindexpense"

    const val COMPILE_SDK = 35
    const val MIN_SDK = 24
    const val TARGET_SDK = 35

    private const val MAJOR_VERSION_NUMBER = 1
    private const val MINOR_VERSION_NUMBER = 0
    private const val PATCH_VERSION_NUMBER = 0
    const val VERSION_CODE = 1

    fun versionName() = "$MAJOR_VERSION_NUMBER.$MINOR_VERSION_NUMBER.$PATCH_VERSION_NUMBER"
}
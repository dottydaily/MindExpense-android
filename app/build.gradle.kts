import com.purkt.convention.plugin.AndroidBuildConfig
import com.purkt.convention.plugin.DefinedBuildType

plugins {
    alias(libs.plugins.convention.androidApplication)
    alias(libs.plugins.convention.composeApplication)
    alias(libs.plugins.convention.flavorApplication)
}

android {
    namespace = AndroidBuildConfig.APPLICATION_ID
    defaultConfig {
        applicationId = AndroidBuildConfig.APPLICATION_ID
        targetSdk = AndroidBuildConfig.TARGET_SDK
        versionCode = AndroidBuildConfig.VERSION_CODE
        versionName = AndroidBuildConfig.versionName()
    }

    buildTypes {
        debug {
            applicationIdSuffix = DefinedBuildType.debug.applicationIdSuffix
        }
        release {
            // Enable ProGuard
            isMinifyEnabled = true

            applicationIdSuffix = DefinedBuildType.release.applicationIdSuffix
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.ui)

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycleRuntimeKtx)
    implementation(libs.androidx.activityCompose)
    implementation(libs.bundles.composeBasic)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}
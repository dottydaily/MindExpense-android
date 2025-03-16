package com.purkt.convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension(val dimensionName: String) {
    environment("environment");
}

@Suppress("EnumEntryName")
enum class DefinedBuildType(val applicationIdSuffix: String? = null) {
    debug(applicationIdSuffix = ".debug"),
    release;
}

@Suppress("EnumEntryName")
enum class DefinedFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    dev(dimension = FlavorDimension.environment, applicationIdSuffix = ".dev"),
    prod(dimension = FlavorDimension.environment);
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: DefinedFlavor) -> Unit = {},
) {
    commonExtension.apply {
        // Register all flavor dimensions.
        FlavorDimension.values().forEach { flavorDimensions += it.name }

        productFlavors {
            DefinedFlavor.values().forEach { flavor ->
                register(flavor.name) {
                    dimension = flavor.dimension.dimensionName // register to their target flavor's dimension.
                    flavorConfigurationBlock(flavor) // apply flavor-specific configuration.

                    // Applied suffix only for application plugin & application flavor.
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (flavor.applicationIdSuffix != null) {
                            applicationIdSuffix = flavor.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
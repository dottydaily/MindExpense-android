import com.purkt.convention.plugin.androidTestImplementation
import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.libs
import com.purkt.convention.plugin.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin for setting up an Android feature module.
 */
class MainAndroidFeatureConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("convention.main.androidLibrary"))
                apply(libs.findPlugin("convention.android.xml"))
                apply(libs.findPlugin("convention.library.compose"))
                apply(libs.findPlugin("convention-library.flavor"))
                apply(libs.findPlugin("kotlin-serialization"))
            }

            dependencies {
                implementation(libs.findLibrary("kotlin-serialization").get())
                implementation(libs.findLibrary("androidx-coreKtx").get())

                implementation(libs.findBundle("composeBasic").get())
                implementation(libs.findLibrary("compose-navigation").get())

                testImplementation(libs.findLibrary("junit").get())
                androidTestImplementation(libs.findLibrary("androidx-junit").get())
                androidTestImplementation(libs.findLibrary("androidx-espressoCore").get())
                androidTestImplementation(libs.findLibrary("compose-navigationTesting").get())
            }
        }
    }
}
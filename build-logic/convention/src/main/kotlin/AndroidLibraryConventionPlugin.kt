import com.android.build.api.dsl.LibraryExtension
import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.configureKotlinAndroid
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin for setting up an Library module.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android-library"))
                apply(libs.findPlugin("kotlin-android"))
                apply(libs.findPlugin("kotlin-parcelize"))
                apply(libs.findPlugin("ksp"))
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }
        }
    }
}
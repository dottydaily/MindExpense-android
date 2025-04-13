import com.android.build.api.dsl.ApplicationExtension
import com.purkt.convention.plugin.androidTestImplementation
import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.configureAndroidCompose
import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin for enabling Compose feature on Application module.
 * This plugin tends to be used together with [MainAndroidApplicationConventionPlugin].
 */
class ApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-composeCompiler"))
                apply(libs.findPlugin("kotlin-serialization"))
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidCompose(this)
            }

            dependencies {
                implementation(libs.findLibrary("kotlin-serialization").get())
                implementation(libs.findLibrary("compose-navigation").get())
                androidTestImplementation(libs.findLibrary("compose-navigationTesting").get())
            }
        }
    }
}
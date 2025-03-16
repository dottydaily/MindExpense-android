import com.android.build.api.dsl.LibraryExtension
import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.configureAndroidCompose
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin for enabling Compose feature for Library module.
 * This plugin tends to be used together with [AndroidLibraryConventionPlugin].
 */
class LibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-composeCompiler"))
            }

            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}
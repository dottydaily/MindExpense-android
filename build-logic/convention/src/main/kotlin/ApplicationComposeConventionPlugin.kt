import com.android.build.api.dsl.ApplicationExtension
import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.configureAndroidCompose
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin for enabling Compose feature for Application module.
 * This plugin tends to be used together with [AndroidApplicationConventionPlugin].
 */
class ApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-composeCompiler"))
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}
import com.android.build.api.dsl.ApplicationExtension
import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.configureKotlinAndroid
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin for setting up an Android application module.
 */
class MainAndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            with(pluginManager) {
                apply(libs.findPlugin("android-application"))
                apply(libs.findPlugin("kotlin-android"))
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
            }
        }
    }
}
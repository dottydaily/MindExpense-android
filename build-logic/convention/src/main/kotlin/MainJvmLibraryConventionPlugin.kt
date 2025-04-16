import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.configureKotlinJvm
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

/**
 * Plugin for setting up an Kotlin-JVM library module.
 */
class MainJvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-jvm"))
                apply(libs.findPlugin("ksp"))
            }

            extensions.configure<JavaPluginExtension> {
                configureKotlinJvm(this)
            }
        }
    }
}
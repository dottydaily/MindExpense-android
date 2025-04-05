import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin for enabling necessary components of Traditional XML view for Application module.
 * This plugin tends to be used together with [AndroidApplicationConventionPlugin].
 */
class XmlConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.findLibrary("androidx-appcompat").get())
                implementation(libs.findLibrary("material").get())
            }
        }
    }
}
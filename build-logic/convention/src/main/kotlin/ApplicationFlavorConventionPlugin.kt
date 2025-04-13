import com.android.build.api.dsl.ApplicationExtension
import com.purkt.convention.plugin.configureFlavors
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin for enabling flavor dimensions on Application module.
 * This plugin tends to be used together with [MainAndroidApplicationConventionPlugin].
 */
class ApplicationFlavorConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<ApplicationExtension> {
                configureFlavors(this)
            }
        }
    }
}
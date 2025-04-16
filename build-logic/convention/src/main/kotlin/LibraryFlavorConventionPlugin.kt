import com.android.build.api.dsl.LibraryExtension
import com.purkt.convention.plugin.configureFlavors
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin for enabling flavor dimensions for Android Library module.
 */
class LibraryFlavorConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                configureFlavors(this)
            }
        }
    }
}
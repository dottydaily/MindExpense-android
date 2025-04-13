import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin for enabling Compose feature on Kotlin-JVM module.
 * This plugin tends to be used together with [MainJvmLibraryConventionPlugin].
 */
class JvmComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-composeCompiler"))
            }

            dependencies {
                // Retrieve the Compose BOM (Bill of Materials).
                val bom = libs.findLibrary("platform-composeBom").get()

                // Import the Compose BOM to manage Compose library versions.
                implementation(platform(bom))

                // Import the Compose runtime to support @Stable, @Immutable annotation.
                // Use for marking any model to be stable which will make compose be able to skip it on recomposition.
                // More detail on : https://developer.android.com/develop/ui/compose/performance/stability/fix#multiple-modules
                implementation(libs.findLibrary("compose-runtime").get())
            }
        }
    }
}
import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin for applying Koin feature on Kotlin-JVM module.
 * This plugin tends to be used together with [MainJvmLibraryConventionPlugin].
 */
class JvmKoinConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                val koinBom = libs.findLibrary("platform-koin").get()
                implementation(platform(koinBom))
                implementation(libs.findLibrary("koin-core").get())
            }
        }
    }
}
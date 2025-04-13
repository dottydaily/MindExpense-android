import com.google.devtools.ksp.gradle.KspExtension
import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.ksp
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin for applying Room feature on Kotlin-JVM module.
 * This plugin tends to be used together with [MainJvmLibraryConventionPlugin].
 */
class JvmRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("ksp"))
            }

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
            }

            dependencies {
                ksp(libs.findLibrary("androidx-roomKsp").get())
                implementation(libs.findLibrary("androidx-roomCommon").get())
            }
        }
    }
}
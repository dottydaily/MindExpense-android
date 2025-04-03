import com.purkt.convention.plugin.apply
import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FirebaseApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("googleServices"))
            }

            dependencies {
                val firebaseBom = libs.findLibrary("platform-firebaseBom").get()
                implementation(platform(firebaseBom))
                implementation(libs.findLibrary("firebase-analytics").get())
            }
        }
    }
}
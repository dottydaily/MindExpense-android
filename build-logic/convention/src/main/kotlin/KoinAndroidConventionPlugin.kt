import com.purkt.convention.plugin.implementation
import com.purkt.convention.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinAndroidConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                val koinBom = libs.findLibrary("platform-koin").get()
                implementation(platform(koinBom))
                implementation(libs.findLibrary("koin-android").get())
                implementation(libs.findBundle("koinCompose").get())
            }
        }
    }
}
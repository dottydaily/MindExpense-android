import com.purkt.convention.plugin.libs
import com.purkt.convention.plugin.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmTestConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                testImplementation(libs.findLibrary("junit").get())
                testImplementation(libs.findLibrary("mockk").get())
                testImplementation(libs.findLibrary("kotlin-test").get())
                testImplementation(libs.findLibrary("kotlinx-coroutinesTest").get())
                testImplementation(libs.findLibrary("koin-testCore").get())
                testImplementation(libs.findLibrary("koin-testJunit4").get())
            }
        }
    }
}
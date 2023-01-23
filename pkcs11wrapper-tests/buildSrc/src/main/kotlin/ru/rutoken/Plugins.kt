import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

inline val PluginDependenciesSpec.idea_ext: PluginDependencySpec
    get() = id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"

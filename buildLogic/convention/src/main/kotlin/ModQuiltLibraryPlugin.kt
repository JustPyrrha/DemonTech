/*
 * Copyright 2023 Pyrrha Wills
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import gay.pyrrha.demontech.libs
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ModQuiltLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("org.quiltmc.loom")
            }

            extensions.configure<LoomGradleExtensionAPI> {
                runtimeOnlyLog4j.set(true)
            }

            dependencies {
                "minecraft"(libs.findLibrary("minecraft").get())
                "mappings"(variantOf(libs.findLibrary("quilt.mappings").get()) {
                    classifier(libs.findVersion("quiltMappingsClassifier").get().requiredVersion)
                })
            }
        }
    }
}

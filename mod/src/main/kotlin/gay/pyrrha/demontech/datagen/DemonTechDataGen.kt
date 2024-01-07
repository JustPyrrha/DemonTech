/*
 * Copyright 2023-2024 Pyrrha Wills
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

package gay.pyrrha.demontech.datagen

import gay.pyrrha.demontech.datagen.provider.ModLanguageProvider
import gay.pyrrha.demontech.datagen.provider.ModModelProvider
import gay.pyrrha.demontech.datagen.provider.ModRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object DemonTechDataGen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        generator.createPack().apply {
            addProvider { output, _ -> ModLanguageProvider(output) }
            addProvider { output, _ -> ModModelProvider(output) }
            addProvider { output, _ -> ModRecipeProvider(output) }
        }
    }
}

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

package gay.pyrrha.demontech

import gay.pyrrha.demontech.block.ModBlocks
import gay.pyrrha.demontech.block.entity.ModBlockEntities
import gay.pyrrha.demontech.item.ModItems
import net.minecraft.util.Identifier
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DemonTech : ModInitializer {
    const val MOD_ID = "demontech"
    val LOGGER: Logger = LoggerFactory.getLogger(DemonTech::class.java)

    override fun onInitialize(mod: ModContainer) {
        LOGGER.info("[DemonTech] Initializing...")
        val startTime = System.currentTimeMillis()

        ModBlocks.init()
        ModBlockEntities.init()
        ModItems.init()

        LOGGER.info("[DemonTech] Initialized. (Took {}ms)", System.currentTimeMillis()-startTime)
    }

    fun id(path: String): Identifier = Identifier(MOD_ID, path)
}

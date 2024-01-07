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

@file:Suppress("MagicNumber")
package gay.pyrrha.demontech.item

import gay.pyrrha.demontech.DemonTech
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings

object ModItems {

    val NETWORK_SCANNER: Item by lazy {
        Item(QuiltItemSettings().maxCount(1).fireproof())
    }

    val ADAPTIVE_THERMAL_PAD: Item by lazy { Item(QuiltItemSettings().maxCount(16)) }
    val THERMAL_PAD: Item by lazy { Item(QuiltItemSettings().maxCount(16)) }

    fun init() {
        register(NETWORK_SCANNER, "network_scanner")
        register(ADAPTIVE_THERMAL_PAD, "adaptive_thermal_pad")
        register(THERMAL_PAD, "thermal_pad")
    }

    private fun register(item: Item, path: String) {
        Registry.register(Registries.ITEM, DemonTech.id(path), item)
    }
}

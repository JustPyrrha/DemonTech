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

package gay.pyrrha.datagenhelper

import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateModelGenerator
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.data.client.model.TexturedModel
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.VariantsBlockStateSupplier
import net.minecraft.state.property.IntProperty

public fun BlockStateModelGenerator.registerHorizontalRotatable(block: Block) {
    registerNorthDefaultHorizontalRotatable(
        block,
        Texture()
            .put(TextureKey.PARTICLE, Texture.getSubId(block, "_front"))
            .put(TextureKey.FRONT, Texture.getSubId(block, "_front"))
            .put(TextureKey.SIDE, Texture.getSubId(block, "_side"))
            .put(TextureKey.TOP, Texture.getSubId(block, "_top"))
            .put(TextureKey.BOTTOM, Texture.getSubId(block, "_bottom"))
    )
}

public fun BlockStateModelGenerator.registerHorizontalRotatableWithChargeLevel(
    block: Block,
    chargeProperty: IntProperty
) {
    val stateSupplier = VariantsBlockStateSupplier.create(block)
    val chargeStates = BlockStateVariantMap.create(chargeProperty)

    for (level in chargeProperty.min()..chargeProperty.max()) {
        val suffix = "_$level"
        val textureId = Texture.getSubId(block, "_front$suffix")
        val modelId = TexturedModel.ORIENTABLE.get(block).texture {
            it.put(TextureKey.FRONT, textureId)
        }.upload(block, suffix, this.modelCollector)

        chargeStates.register(
            level,
            BlockStateVariant.create()
                .put(
                    VariantSettings.MODEL,
                    modelId
                )
            )
    }

    stateSupplier.coordinate(chargeStates)
    stateSupplier.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())

    this.blockStateCollector.accept(stateSupplier)
}


private fun IntProperty.min() = this.values.min()
private fun IntProperty.max() = this.values.max()

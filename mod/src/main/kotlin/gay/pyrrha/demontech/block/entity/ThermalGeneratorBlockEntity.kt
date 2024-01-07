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

package gay.pyrrha.demontech.block.entity

import gay.pyrrha.block.generated.ThermalGeneratorBlockEntitySync
import gay.pyrrha.block.generated.ThermalGeneratorBlockEntitySyncData
import gay.pyrrha.demontech.block.ModProperties
import gay.pyrrha.sync.Sync
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

@Sync
class ThermalGeneratorBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(ModBlockEntities.THERMAL_GENERATOR, pos, state) {

    @Sync
    var active: Boolean = false
        internal set

    @Sync
    private var energyBuffer: Int = 0

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(
            ThermalGeneratorBlockEntitySync.write(
                nbt,
                ThermalGeneratorBlockEntitySyncData(
                    active,
                    energyBuffer
                )
            )
        )
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        ThermalGeneratorBlockEntitySync.read(nbt) {
            this@ThermalGeneratorBlockEntity.active = active
            this@ThermalGeneratorBlockEntity.energyBuffer = energyBuffer
        }
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener> =
        BlockEntityUpdateS2CPacket.of(this)

    override fun toSyncedNbt(): NbtCompound {
        val nbt = NbtCompound()
        writeNbt(nbt)
        return nbt
    }

    companion object {
        private const val MAX_ENERGY = 600.0

        @Suppress("CyclomaticComplexMethod") // scored 16, max 15
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: ThermalGeneratorBlockEntity) {
            if (!world.isClient) {
                if(entity.active && entity.energyBuffer < MAX_ENERGY) {
                    entity.energyBuffer++
                }

                @Suppress("MagicNumber")
                when ((entity.energyBuffer / MAX_ENERGY)) {
                    in 0.0..<0.1 -> updateIfNot(0, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.1..<0.2 -> updateIfNot(1, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.2..<0.3 -> updateIfNot(2, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.3..<0.4 -> updateIfNot(3, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.4..<0.5 -> updateIfNot(4, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.5..<0.6 -> updateIfNot(5, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.6..<0.7 -> updateIfNot(6, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.7..<0.8 -> updateIfNot(7, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.8..<0.9 -> updateIfNot(8, ModProperties.CHARGE_LEVEL, state, world, pos)
                    in 0.9..<1.0 -> updateIfNot(9, ModProperties.CHARGE_LEVEL, state, world, pos)
                    1.0 -> updateIfNot(10, ModProperties.CHARGE_LEVEL, state, world, pos)
                    else -> {}
                }
            }
        }

        private fun updateIfNot(value: Int, prop: IntProperty, state: BlockState, world: World, pos: BlockPos) {
            if(state.get(prop) != value) {
                world.setBlockState(pos, state.with(prop, value))
            }
        }
    }
}

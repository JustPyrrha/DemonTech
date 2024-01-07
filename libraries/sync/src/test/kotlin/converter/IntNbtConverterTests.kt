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

package converter

import gay.pyrrha.sync.NbtTypeIds
import gay.pyrrha.sync.converter.IntNbtConverter
import net.minecraft.nbt.NbtCompound
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IntNbtConverterTests {
    
    @Test
    fun `is valid for kotlin Int`() {
        // Given
        val kClass = Int::class.qualifiedName!!
        
        // When
        val isValid = IntNbtConverter.validFor(kClass)
        
        // Then
        assertTrue(isValid)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1, 1, Int.MAX_VALUE, Int.MIN_VALUE])
    fun `can read ints`(value: Int) {
        // Given
        val name = "testInt"
        val compound = NbtCompound()
        compound.putInt(name, value)

        // When
        val testInt = IntNbtConverter.read(compound, name)

        // Then
        assertEquals(value, testInt)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1, 1, Int.MAX_VALUE, Int.MIN_VALUE])
    fun `can write ints`(value: Int) {
        // Given
        val name = "testInt"
        val compound = NbtCompound()

        // When
        IntNbtConverter.write(compound, name, value)

        // Then
        assertTrue(compound.contains(name, NbtTypeIds.INT))
        assertEquals(value, compound.getInt(name))
    }
}

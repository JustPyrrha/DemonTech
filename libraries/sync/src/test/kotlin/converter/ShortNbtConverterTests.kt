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
import gay.pyrrha.sync.converter.ShortNbtConverter
import net.minecraft.nbt.NbtCompound
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShortNbtConverterTests {
    @Test
    fun `is valid for kotlin Short`() {
        // Given
        val kClass = Short::class.qualifiedName!!

        // When
        val isValid = ShortNbtConverter.validFor(kClass)

        // Then
        assertTrue(isValid)
    }

    @ParameterizedTest
    @ValueSource(shorts = [0, -1, 1, Short.MAX_VALUE, Short.MIN_VALUE])
    fun `can read shorts`(value: Short) {
        // Given
        val name = "testShort"
        val compound = NbtCompound()
        compound.putShort(name, value)

        // When
        val testShort = ShortNbtConverter.read(compound, name)

        // Then
        assertEquals(value, testShort)
    }

    @ParameterizedTest
    @ValueSource(shorts = [0, -1, 1, Short.MAX_VALUE, Short.MIN_VALUE])
    fun `can write shorts`(value: Short) {
        // Given
        val name = "testShort"
        val compound = NbtCompound()

        // When
        ShortNbtConverter.write(compound, name, value)

        // Then
        assertTrue(compound.contains(name, NbtTypeIds.SHORT))
        assertEquals(value, compound.getShort(name))
    }
}

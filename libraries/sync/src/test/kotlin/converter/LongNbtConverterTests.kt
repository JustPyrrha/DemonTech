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
import gay.pyrrha.sync.converter.LongNbtConverter
import net.minecraft.nbt.NbtCompound
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LongNbtConverterTests {
    @Test
    fun `is valid for kotlin Long`() {
        // Given
        val kClass = Long::class.qualifiedName!!

        // When
        val isValid = LongNbtConverter.validFor(kClass)

        // Then
        assertTrue(isValid)
    }

    @ParameterizedTest
    @ValueSource(longs = [0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE])
    fun `can read longs`(value: Long) {
        // Given
        val name = "testLong"
        val compound = NbtCompound()
        compound.putLong(name, value)

        // When
        val testLong = LongNbtConverter.read(compound, name)

        // Then
        assertEquals(value, testLong)
    }

    @ParameterizedTest
    @ValueSource(longs = [0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE])
    fun `can write longs`(value: Long) {
        // Given
        val name = "testLong"
        val compound = NbtCompound()

        // When
        LongNbtConverter.write(compound, name, value)

        // Then
        assertTrue(compound.contains(name, NbtTypeIds.LONG))
        assertEquals(value, compound.getLong(name))
    }
}

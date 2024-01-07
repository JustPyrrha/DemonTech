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
import gay.pyrrha.sync.converter.ByteArrayNbtConverter
import net.minecraft.nbt.NbtCompound
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ByteArrayNbtConverterTests {

    @Test
    fun `is valid for kotlin ByteArray`() {
        // Given
        val name = ByteArray::class.qualifiedName!!

        // When
        val isValid = ByteArrayNbtConverter.validFor(name)

        // Then
        assertTrue(isValid)
    }

    @Test
    fun `can read byte array`() {
        // Given
        val name = "testByteArray"
        val value = byteArrayOf(0, 1, -1, Byte.MAX_VALUE, Byte.MIN_VALUE)
        val compound = NbtCompound()
        compound.putByteArray(name, value)

        // When
        val testByteArray = ByteArrayNbtConverter.read(compound, name)

        // Then
        assertEquals(value, testByteArray)
    }

    @Test
    fun `can write byte array`() {
        // Given
        val name = "testByteArray"
        val value = byteArrayOf(0, 1, -1, Byte.MAX_VALUE, Byte.MIN_VALUE)
        val compound = NbtCompound()

        // When
        ByteArrayNbtConverter.write(compound, name, value)

        // Then
        assertTrue(compound.contains(name, NbtTypeIds.BYTE_ARRAY))
        assertEquals(value, compound.getByteArray(name))
    }
}

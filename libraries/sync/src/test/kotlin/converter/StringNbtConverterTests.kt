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
import gay.pyrrha.sync.converter.StringNbtConverter
import net.minecraft.nbt.NbtCompound
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StringNbtConverterTests {
    @Test
    fun `is valid for kotlin String`() {
        // Given
        val kClass = String::class.qualifiedName!!

        // When
        val isValid = StringNbtConverter.validFor(kClass)

        // Then
        assertTrue(isValid)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "test"])
    fun `can read strings`(value: String) {
        // Given
        val name = "testStrings"
        val compound = NbtCompound()
        compound.putString(name, value)

        // When
        val testString = StringNbtConverter.read(compound, name)

        // Then
        assertEquals(value, testString)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "test"])
    fun `can write strings`(value: String) {
        // Given
        val name = "testString"
        val compound = NbtCompound()

        // When
        StringNbtConverter.write(compound, name, value)

        // Then
        assertTrue(compound.contains(name, NbtTypeIds.STRING))
        assertEquals(value, compound.getString(name))
    }
}

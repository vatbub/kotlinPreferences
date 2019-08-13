/*-
 * #%L
 * Greeting - A Sample Kotlin App
 * %%
 * Copyright (C) 2016 - 2019 Frederik Kammel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.github.vatbub.kotlin.preferences

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PreferencesTest {
    @Test
    fun setTest() {
        var serializerCalled = false
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, { it.toInt() }, {
            serializerCalled = true
            it.toString()
        }) {}

        val keyValueProvider = MemoryKeyValueProvider()
        val preferences = Preferences(keyValueProvider)
        val value = 5678
        preferences[dummyIntKey] = value

        Assertions.assertTrue(serializerCalled)
        Assertions.assertEquals(value.toString(), keyValueProvider.contents[dummyIntKey.uniqueName])
    }

    @Test
    fun setAndGetTest() {
        var serializerCalled = false
        var parserCalled = false
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, {
            parserCalled = true
            it.toInt()
        }, {
            serializerCalled = true
            it.toString()
        }) {}

        val keyValueProvider = MemoryKeyValueProvider()
        val preferences = Preferences(keyValueProvider)
        val value = 5678
        preferences[dummyIntKey] = value
        Assertions.assertTrue(serializerCalled)

        Assertions.assertEquals(value, preferences[dummyIntKey])
        Assertions.assertTrue(parserCalled)
    }

    @Test
    fun getIfExistsNonExistentKeyTest() {
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, { it.toInt() }, { it.toString() }) {}
        Assertions.assertNull(Preferences(MemoryKeyValueProvider()).getIfExists(dummyIntKey))
    }

    @Test
    fun getOrDefaultNonExistentKeyTest() {
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, { it.toInt() }, { it.toString() }) {}
        Assertions.assertEquals(dummyIntKey.defaultValue, Preferences(MemoryKeyValueProvider())[dummyIntKey])
    }

    @Test
    fun positiveContainsKeyTest() {
        val preferences = Preferences(MemoryKeyValueProvider())
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, { it.toInt() }, { it.toString() }) {}
        preferences[dummyIntKey] = 567
        Assertions.assertTrue(preferences.containsKey(dummyIntKey))
    }

    @Test
    fun negativeContainsKeyTest() {
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, { it.toInt() }, { it.toString() }) {}
        Assertions.assertFalse(Preferences(MemoryKeyValueProvider()).containsKey(dummyIntKey))
    }

    @Test
    fun positiveRemoveKeyTest() {
        val preferences = Preferences(MemoryKeyValueProvider())
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, { it.toInt() }, { it.toString() }) {}
        val value = 567
        preferences[dummyIntKey] = value
        Assertions.assertTrue(preferences.containsKey(dummyIntKey))
        Assertions.assertEquals(value, preferences.removeKey(dummyIntKey))
        Assertions.assertFalse(preferences.containsKey(dummyIntKey))
    }

    @Test
    fun negativeRemoveKeyTest() {
        val preferences = Preferences(MemoryKeyValueProvider())
        val dummyIntKey = object : Key<Int>("dummyIntKey", 12345, { it.toInt() }, { it.toString() }) {}

        Assertions.assertFalse(preferences.containsKey(dummyIntKey))
        Assertions.assertNull(preferences.removeKey(dummyIntKey))
        Assertions.assertFalse(preferences.containsKey(dummyIntKey))
    }
}

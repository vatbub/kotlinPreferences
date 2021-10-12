/*-
 * #%L
 * kotlinPreferences-android
 * %%
 * Copyright (C) 2019 - 2021 Frederik Kammel
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
package com.github.vatbub.kotlin.preferences.android

import com.github.vatbub.kotlin.preferences.KeyValueProvider
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

abstract class KeyValueProviderTest<T : KeyValueProvider> {
    abstract fun newKeyValueProvider(): T
    abstract fun getCloneOf(keyValueProvider: T): T

    @Test
    fun notFoundTest() {
        val keyValueProvider = newKeyValueProvider()
        Assertions.assertNull(keyValueProvider["randomKey"])
    }

    @Test
    fun setAndGetTest() {
        val keyValueProvider = newKeyValueProvider()
        val key = "sampleKey1"
        val value = "value1"

        keyValueProvider[key] = value
        Assertions.assertEquals(value, keyValueProvider[key])
    }

    @Test
    fun setNullTest() {
        val keyValueProvider = newKeyValueProvider()
        val key = "sampleKey1"
        val value = "value1"

        keyValueProvider[key] = value
        Assertions.assertEquals(value, keyValueProvider[key])
        keyValueProvider[key] = null
        Assertions.assertNull(keyValueProvider[key])
    }

    @Test
    fun persistenceTest() {
        val keyValueProvider1 = newKeyValueProvider()
        if (!keyValueProvider1.isPersistent) return

        val key = "sampleKey2"
        val value = "value2"
        keyValueProvider1[key] = value

        val keyValueProvider2 = getCloneOf(keyValueProvider1)

        Assertions.assertNotSame(keyValueProvider1, keyValueProvider2)

        Assertions.assertEquals(value, keyValueProvider2[key])
    }
}

class SharedPreferencesKeyValueProviderTest : KeyValueProviderTest<SharedPreferencesKeyValueProvider>() {
    override fun newKeyValueProvider() = SharedPreferencesKeyValueProvider(SharedPreferencesImpl())

    override fun getCloneOf(keyValueProvider: SharedPreferencesKeyValueProvider) = SharedPreferencesKeyValueProvider(keyValueProvider.sharedPreferences)

}

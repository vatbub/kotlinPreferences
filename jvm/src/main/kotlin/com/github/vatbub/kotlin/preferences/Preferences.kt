/*-
 * #%L
 * Kotlin Preferences
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

/**
 * Provides key-value-pairs for applications.
 * @param keyValueProvider The underlying key-value-store.
 */
class Preferences(private val keyValueProvider: KeyValueProvider) {

    /**
     * Returns the value associated with the specified key if it exists.
     * @param key The key to get the value for
     * @return The value associated with the key if it exists or `null` otherwise
     */
    fun <T> getIfExists(key: Key<T>): T? {
        val stringValue = keyValueProvider[key.uniqueName] ?: return null
        return key.parser(stringValue)
    }

    /**
     * Returns the value with the associated key. If the key cannot be found, the default value of the key is returned.
     * @param key The key to get the value for
     * @return The value associated with the key or the key's default value if the key cannot be found
     */
    fun <T> getOrDefault(key: Key<T>) = getIfExists(key) ?: key.defaultValue

    /**
     * Checks whether the specified key has a value associated with it.
     */
    fun <T> containsKey(key: Key<T>) = keyValueProvider[key.uniqueName] != null

    /**
     * @see getOrDefault
     */
    operator fun <T> get(key: Key<T>) = getOrDefault(key)

    /**
     * Associates the specified value with the specified key.
     * If the key already had a value associated with it, the previous value is overwritten.
     * @param key The key to set the value for
     * @param value The value to associate with [key]
     */
    operator fun <T> set(key: Key<T>, value: T?) {
        val stringValue = if (value == null) null else key.serializer(value)
        keyValueProvider[key.uniqueName] = stringValue
    }

    /**
     * Removes the specified key from this key-value-store.
     * @param key The key to remove the value of
     * @return The value that was previously associated with [key] or `null` if no value was associated with [key]
     */
    fun <T> removeKey(key: Key<T>): T? {
        val previousValue = getIfExists(key)
        set(key, null)
        return previousValue
    }
}

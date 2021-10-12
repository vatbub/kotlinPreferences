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
 * This is an interface that wraps simple string-based key-value-stores.
 * Implementations of this interface are used in [Preferences] to store keys and values internally.
 */
interface KeyValueProvider {
    /**
     * Stores a given [key] with its [value]. If the [key] already has a value associated with it, it shall be overwritten.
     * When [value]`== null`, the corresponding key-value-pair shall be deleted from the key-value-store
     * @param key The key to store the value to.
     * @param value The value to be associated with [key]
     */
    operator fun set(key: String, value: String?)

    /**
     * Retrieves the value for the given [key].
     * @param key The key to get the value for
     * @return The value that is associated with [key] or `null` if no value was ever associated with [key]
     */
    operator fun get(key: String): String?

    /**
     * Shall always be `true` if the stored data is able to survive application restarts and `false` if not.
     */
    val isPersistent:Boolean
}

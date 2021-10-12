/*-
 * #%L
 * kotlinPreferences
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
package com.github.vatbub.kotlin.preferences

/**
 * Saves its contents in memory inside a [MutableMap].
 * @param contents Map to store the data in
 */
class MemoryKeyValueProvider(val contents: MutableMap<String, String> = mutableMapOf()) : KeyValueProvider {
    override fun set(key: String, value: String?) {
        if (value == null) {
            contents.remove(key)
            return
        }
        contents[key] = value
    }

    override fun get(key: String) = contents[key]

    override val isPersistent = false

}

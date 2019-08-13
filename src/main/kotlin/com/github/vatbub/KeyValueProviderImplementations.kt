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
package com.github.vatbub

import java.io.File
import java.util.*

class PreferenceFileKeyValueProvider(private val properties: Properties) : KeyValueProvider {
    override fun set(key: String, value: String?) {
        properties[key] = value
    }

    override fun get(key: String) = properties[key] as String?

    constructor(file: File) : this(Properties()) {
        file.inputStream().use { properties.load(it) }
    }
}

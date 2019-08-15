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

import android.content.SharedPreferences
import java.io.File
import java.util.*

/**
 * Stores its data inside a Java-*.properties-file.
 * @param propertiesFile The location of the file to read from and store the data in. If the file does not exist, it wil be created
 * when calling [KeyValueProvider.set] for the first time.
 */
class PropertiesFileKeyValueProvider(val propertiesFile: File) : KeyValueProvider {
    override val isPersistent = true
    private val properties = Properties()

    init {
        if (propertiesFile.isDirectory) throw IllegalArgumentException("The specified file is a directory")
        if (propertiesFile.exists())
            propertiesFile.inputStream().use { properties.load(it) }
    }

    override fun set(key: String, value: String?) {
        if (value == null)
            properties.remove(key)
        else
            properties[key] = value
        synchronized(propertiesFile) {
            propertiesFile.outputStream().use { properties.store(it, "This file stores the settings of one of your applications. Deleting it may partially or completely reset the corresponding application.") }
        }
    }

    override fun get(key: String) = properties[key] as String?
}

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

/**
 * Use this when on Android. Stores its data inside the supplied [sharedPreferences].
 * @param sharedPreferences The [SharedPreferences]-instance to store the data in.
 */
class SharedPreferencesKeyValueProvider(val sharedPreferences: SharedPreferences) : KeyValueProvider {
    override fun set(key: String, value: String?) {
        if (value == null) {
            sharedPreferences.edit().remove(key).apply()
            return
        }
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun get(key: String): String? = sharedPreferences.getString(key, null)
    override val isPersistent = true
}

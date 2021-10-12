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
package com.github.vatbub.kotlin.preferences.android

import android.content.SharedPreferences

class SharedPreferencesImpl(internal val contents: MutableMap<String, Any> = mutableMapOf()) : SharedPreferences {

    override fun contains(key: String) = contents.containsKey(key)

    override fun getBoolean(key: String, defValue: Boolean) = contents[key]as? Boolean ?: defValue

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInt(key: String, defValue: Int) = contents[key] as? Int ?: defValue

    override fun getAll() = contents.toMutableMap()

    override fun edit() = SharedPreferencesEditorImpl(this)

    override fun getLong(key: String, defValue: Long) = contents[key] as? Long ?: defValue

    override fun getFloat(key: String, defValue: Float) = contents[key] as? Float ?: defValue

    override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getString(key: String, defValue: String?) = contents[key] as String?
}

class SharedPreferencesEditorImpl(private val sharedPreferencesImpl: SharedPreferencesImpl) : SharedPreferences.Editor {
    override fun clear(): SharedPreferences.Editor {
        sharedPreferencesImpl.contents.clear()
        return this
    }

    override fun putLong(key: String, value: Long): SharedPreferences.Editor {
        sharedPreferencesImpl.contents[key] = value
        return this
    }

    override fun putInt(key: String, value: Int): SharedPreferences.Editor {
        sharedPreferencesImpl.contents[key] = value
        return this
    }

    override fun remove(key: String): SharedPreferences.Editor {
        sharedPreferencesImpl.contents.remove(key)
        return this
    }

    override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
        sharedPreferencesImpl.contents[key] = value
        return this
    }

    override fun putStringSet(key: String, values: MutableSet<String>?): SharedPreferences.Editor {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun commit() = true

    override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
        sharedPreferencesImpl.contents[key] = value
        return this
    }

    override fun apply() {}

    override fun putString(key: String, value: String): SharedPreferences.Editor {
        sharedPreferencesImpl.contents[key] = value
        return this
    }
}

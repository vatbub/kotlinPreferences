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

import android.content.SharedPreferences
import com.github.vatbub.kotlin.preferences.KeyValueProvider

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

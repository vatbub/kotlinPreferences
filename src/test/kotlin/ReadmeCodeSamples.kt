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
import com.github.vatbub.kotlin.preferences.Key
import com.github.vatbub.kotlin.preferences.Preferences
import com.github.vatbub.kotlin.preferences.PropertiesFileKeyValueProvider
import java.io.File

// Do this somewhere globally, so that you have access to the preferences
// object everywhere

// specify the backing storage
val keyValueProvider = PropertiesFileKeyValueProvider(File("mySettings.properties"))
val preferences = Preferences(keyValueProvider)

// Define keys
object MyFirstSetting : Key<Int>(uniqueName = "myFirstSetting", defaultValue = 12345, parser = { it.toInt() }, serializer = { it.toString() })
object MySecondSetting : Key<Boolean>(uniqueName = "mySecondSetting", defaultValue = true, parser = { it.toBoolean() }, serializer = { it.toString() })

// Save a preference
fun saveMyPreference() {
    // Typesafe, the compiler knows that this key only accepts Int
    preferences[MyFirstSetting] = 500
}

// retrieve a preference (or its default value)
fun getMyPreference() {
    // Typesafe, the compiler knows that this key always returns Int
    val value: Int = preferences[MyFirstSetting]
}

// optionally retrieve a preference
fun getMyPreferenceOrNull() {
    val value: Int? = preferences.getIfExists(MyFirstSetting)
}

// complex values
data class MyValue(val firstProperty: String = "", val secondProperty: Int = 0)

object MyComplexSetting : Key<MyValue>(
        uniqueName = "myComplexSetting",
        defaultValue = MyValue(),
        parser = { it: String ->
            val split = it.split(";")
            MyValue(split[0], split[1].toInt())
        },
        serializer = { it: MyValue -> "${it.firstProperty};${it.secondProperty}" })

// setting this preference is as simple as saving any other preference
fun setMyComplexPreference() {
    preferences[MyComplexSetting] = MyValue("Hello", 12345)
}

// And retrieving this preference also works in the same way
fun retrieveMyComplexPreference() {
    val value: MyValue = preferences[MyComplexSetting]
    val optionalValue: MyValue? = preferences.getIfExists(MyComplexSetting)
}

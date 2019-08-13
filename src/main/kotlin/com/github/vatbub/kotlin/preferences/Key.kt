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
 * The base class for keys used in the [Preferences]-class.
 * @param T The type of value that can be associated with this key.
 * @param uniqueName A unique and constant string representation of this key. The keys and values are internally stored as strings. This [uniqueName] must therefore be unique within this application. If non-unique keys are used, behaviour is undefined.
 * @param defaultValue A sensible default value for this key. See [Preferences.getOrDefault] for more infos.
 * @param parser A function that converts values from their string representation into their object representation. This function should be stateless.
 * @param serializer A function that converts values from their object representation to their string representation. Hint: `obj == key.parser(key.serializer(obj))` should always evaluate to `true`.
 */
abstract class Key<T>(val uniqueName: String, val defaultValue: T, val parser: (String) -> T, val serializer: (T) -> String)

# kotlinPreferences
It's a problem we all know: Every application has at least some values 
or user settings that it needs to store somehow and different platforms 
have different solutions:
Java has its `*.properties` files, Android has the `SharedPreferences`, 
but they all have two problems:
1. They can only store primitives and `String`s 
2. Their apis are often very convoluted.

This is where `kotlinPreferences` comes into play: A simple, yet very complete 
api to store any kind of object in a persistent (or even volatile) manner.

## Download
### Maven
```xml
<project>
    <repositories>
        <repository>
            <snapshots/>
            <id>snapshots</id>
            <name>libs-snapshot</name>
            <url>https://oss.jfrog.org/artifactory/libs-snapshot</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>com.github.vatbub</groupId>
            <artifactId>kotlinPreferences</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```

## Usage
It's so simple:
```kotlin
import com.github.vatbub.kotlin.preferences.*
import java.io.File

// Do this somewhere globally, so that you have access to the preferences
// object everywhere

// specify the backing storage
val keyValueProvider = PropertiesFileKeyValueProvider(File("mySettings.properties"))
val preferences = Preferences(keyValueProvider)

// Define keys
object MyFirstSetting : Key<Int>(uniqueName = "myFirstSetting", defaultValue = 12345, parser = { it.toInt() }, serializer = { it.toString() })
object MySecondSetting : Key<Boolean>(uniqueName = "mySecondSetting", defaultValue = true, parser = { it.toBoolean() }, serializer = { it.toString() })

// The following can then be done anywhere in your code

// Save a preference
// Typesafe, the compiler knows that this key only accepts Int
preferences[MyFirstSetting] = 500

// retrieve a preference (or its default value)
// Typesafe, the compiler knows that this key always returns Int
val value: Int = preferences[MyFirstSetting]

// optionally retrieve a preference
val value: Int? = preferences.getIfExists(MyFirstSetting)
```

But it doesn't stop there! You can save any kind of object, as long as 
you provide a corresponding serializer and parser:

```kotlin
// Just an example, this could be any class
data class MyValue(val firstProperty: String = "", val secondProperty: Int = 0)

object MyComplexSetting : Key<MyValue>(
        uniqueName = "myComplexSetting",
        defaultValue = MyValue(),
        parser = { it: String -> // This method is called to convert a String into a MyVale-instance
            val split = it.split(";")
            MyValue(split[0], split[1].toInt())
        },
        // This method is called to convert MyValue-instances into strings
        serializer = { it: MyValue -> "${it.firstProperty};${it.secondProperty}" })

// setting this preference is as simple as saving any other preference
preferences[MyComplexSetting] = MyValue("Hello", 12345)

// And retrieving this preference also works in the same way
val value: MyValue = preferences[MyComplexSetting]
val optionalValue: MyValue? = preferences.getIfExists(MyComplexSetting)
``` 

### Choosing an appropriate backing storage (Android developers, listen up!)
As you have probably seen from the code above, all values are internally
serialized to strings. But what happens then? 

`kotlinPreferences` comes with several `KeyValueProvider`s, which are
the backing storage behind the `Preferences`-class. 
Each of them is appropriate for different situations:

| Name                                                                               | Appropriate for                                                       | Backing storage                              |
|:-----------------------------------------------------------------------------------|:----------------------------------------------------------------------|:---------------------------------------------|
| `PropertiesFileKeyValueProvider` (This is the provider used in the examples above) | Java/Kotlin desktop applications                                      | A Java `*.properties`-file                   |
| `SharedPreferencesKeyValueProvider`                                                | Android applications                                                  | An instance of Android's `SharedPreferences` |
| `MemoryKeyValueProvider`                                                           | Any Java/Kotlin application which does not require persistent storage | An plain old `HashMap`                       |

## Compiling
This project uses Maven. Therefore, all you need to do is the following:

1. [Download](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and install the JDK 8 or higher (I hope you already did that!)
2. [Download](https://maven.apache.org/download.cgi) and [install](https://maven.apache.org/install.html) Apache Maven (if you haven't already)
3. Clone this repo and `cd` into it
4. Run `mvn install` to compile the jar and install it into your local Maven repository

You will find the compiled jar in a subfolder called `target`.

## License
Copyright (C) 2016 - 2019 Frederik Kammel

Licensed under the Apache License, Version 2.0 (the "License");

You may not use this library except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

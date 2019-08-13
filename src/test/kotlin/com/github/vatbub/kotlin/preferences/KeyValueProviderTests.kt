package com.github.vatbub.kotlin.preferences

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

abstract class KeyValueProviderTest<T : KeyValueProvider> {
    abstract fun newKeyValueProvider(): T
    abstract fun getCloneOf(keyValueProvider: T): T

    @Test
    fun notFoundTest() {
        val keyValueProvider = newKeyValueProvider()
        Assertions.assertNull(keyValueProvider["randomKey"])
    }

    @Test
    fun setAndGetTest() {
        val keyValueProvider = newKeyValueProvider()
        val key = "sampleKey1"
        val value = "value1"

        keyValueProvider[key] = value
        Assertions.assertEquals(value, keyValueProvider[key])
    }

    @Test
    fun persistenceTest() {
        val keyValueProvider1 = newKeyValueProvider()
        if (!keyValueProvider1.isPersistent) return

        val key = "sampleKey2"
        val value = "value2"
        keyValueProvider1[key] = value

        val keyValueProvider2 = getCloneOf(keyValueProvider1)

        Assertions.assertNotSame(keyValueProvider1, keyValueProvider2)

        Assertions.assertEquals(value, keyValueProvider2[key])
    }
}

class PropertiesFileKeyValueProviderTest : KeyValueProviderTest<PropertiesFileKeyValueProvider>() {
    companion object {
        @JvmStatic
        @TempDir
        lateinit var tempDir: File

        fun getTempFile(baseFileName: String = "tempFile", fileExtension: String = "properties"): File {
            var counter = 0
            while (createFileObject(tempDir, baseFileName, counter, fileExtension).exists())
                counter++

            val tempFile = createFileObject(tempDir, baseFileName, counter, fileExtension)
            tempFile.createNewFile()
            return tempFile
        }

        private fun createFileObject(baseDir: File, baseFileName: String, counter: Int, fileExtension: String) = baseDir.toPath().resolve("$baseFileName$counter.$fileExtension").toFile()
    }

    override fun newKeyValueProvider() = PropertiesFileKeyValueProvider(getTempFile())

    override fun getCloneOf(keyValueProvider: PropertiesFileKeyValueProvider) = PropertiesFileKeyValueProvider(keyValueProvider.propertiesFile)
}

class MemoryKeyValueProviderTest : KeyValueProviderTest<MemoryKeyValueProvider>() {
    override fun newKeyValueProvider() = MemoryKeyValueProvider()
    override fun getCloneOf(keyValueProvider: MemoryKeyValueProvider) =
            MemoryKeyValueProvider(keyValueProvider.contents)
}

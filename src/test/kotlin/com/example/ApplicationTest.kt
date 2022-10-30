package com.example

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.plugins.*
import java.io.File
import java.nio.file.Files
import kotlin.io.path.name
import kotlin.io.path.writeLines

class ApplicationTest {

    @Test
    fun `check file searcher`() {
        val file = Files.createTempFile(null, null)
        file.writeLines(listOf("hello wonderful world", "good bye dear dream"))

        assertTrue { isFileContainsWords(file.toFile(), setOf()) }
        assertTrue { isFileContainsWords(file.toFile(), setOf("hello", "dear")) }
        assertTrue { isFileContainsWords(file.toFile(), setOf("bye", "dream")) }
        assertTrue { isFileContainsWords(file.toFile(), setOf("hello", "dream")) }
        assertTrue {
            isFileContainsWords(
                file.toFile(),
                setOf("hello", "wonderful", "world", "good", "bye", "dear", "dream")
            )
        }

        assertFalse { isFileContainsWords(file.toFile(), setOf("bye", "friend")) }
        assertFalse {
            isFileContainsWords(
                file.toFile(),
                setOf("hello", "wonderful", "world", "good", "bye", "dear", "dream", "imposter")
            )
        }
    }

}

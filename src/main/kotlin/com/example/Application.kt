package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import java.nio.file.Files
import java.nio.file.Paths

val folderPath: String = System.getenv("TEXT_FOLDER") ?: error("Expected envvar 'TEXT_FOLDER'")

fun main() {
    check(Files.exists(Paths.get(folderPath))) { "Path doesn't exists: $folderPath" }
    check(Files.isDirectory(Paths.get(folderPath))) { "Path is not a directory: $folderPath" }
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}

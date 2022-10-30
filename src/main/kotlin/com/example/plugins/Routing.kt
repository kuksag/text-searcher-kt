package com.example.plugins

import com.example.folderPath
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.io.InputStream

fun isFileContainsWords(file: File, words: Set<String>): Boolean {
    val foundWords = mutableSetOf<String>()
    val inputStream: InputStream = file.inputStream()
    inputStream.bufferedReader().forEachLine { line ->
        // It can be modified regarding the goal of matching: either word match or char sequence match
        val wordsInLine = line.split(" ")
        foundWords.addAll(words.filter { wordsInLine.contains(it) }.toSet())
    }
    return foundWords.size == words.size
}

fun Application.configureRouting() {
    routing {
        post("/") {
            val words = call.receiveText().split(" ").toSet()
            val result =
                File(folderPath).walk().filter { it.isFile && isFileContainsWords(it, words) }.map { it.name }.toSet()
            call.respondText(result.toString())
        }
    }
    routing {
    }
}

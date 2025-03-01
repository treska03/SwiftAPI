package io.github.treska.swiftapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SwiftApiApplication

fun main(args: Array<String>) {
    runApplication<SwiftApiApplication>(*args)
}

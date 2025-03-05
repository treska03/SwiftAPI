package io.github.treska.swiftapi.dto

import org.springframework.http.HttpStatus

data class ErrorResponseDto(
    val title: String,
    val status: HttpStatus,
    val detail: String?
)

package io.github.treska.swiftapi.controller

import io.github.treska.swiftapi.dto.ErrorResponseDto
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
class GenericExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponseDto> {
        val responseStatus = e.javaClass.getAnnotation(ResponseStatus::class.java)

        val err = ErrorResponseDto(
            title = e.javaClass.simpleName,
            status = responseStatus?.value ?: HttpStatus.INTERNAL_SERVER_ERROR,
            detail = e.message
        )

        if (responseStatus != null) {
            return ResponseEntity.status(responseStatus.value).body<ErrorResponseDto>(err)
        }

        return ResponseEntity.internalServerError().body<ErrorResponseDto>(err)
    }
}
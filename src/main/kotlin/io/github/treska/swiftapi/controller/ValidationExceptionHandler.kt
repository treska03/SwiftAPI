package io.github.treska.swiftapi.controller

import io.github.treska.swiftapi.dto.ErrorResponseDto
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDto> {
        val error = e.bindingResult.fieldError

        val err = ErrorResponseDto(
            title = e.javaClass.simpleName,
            status = HttpStatus.BAD_REQUEST,
            detail = error?.defaultMessage ?: e.message
        )
        return ResponseEntity.badRequest().body<ErrorResponseDto>(err)
    }
}
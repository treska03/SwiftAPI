package io.github.treska.swiftapi.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
data class BankNotFoundException(
    override val message: String
) : IllegalArgumentException()

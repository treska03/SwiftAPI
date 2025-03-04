package io.github.treska.swiftapi.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.FileNotFoundException

@ResponseStatus(value = HttpStatus.NOT_FOUND)
data class SwiftCodesCSVNotFoundException(
    override val message: String = "swift_codes.csv not found in resources.",
) : FileNotFoundException()
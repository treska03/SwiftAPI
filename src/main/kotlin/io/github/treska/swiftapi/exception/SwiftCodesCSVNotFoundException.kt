package io.github.treska.swiftapi.exception

import java.io.FileNotFoundException

data class SwiftCodesCSVNotFoundException(
    override val message: String = "swift_codes.csv not found in resources.",
) : FileNotFoundException()

package io.github.treska.swiftapi.dto

import jakarta.validation.constraints.Size

interface BankDetailsDto {
    val address: String
    val bankName: String
    val countryISO2: String
    val countryName: String
    val isHeadquarter: Boolean

    @get:Size(min = 11, max = 11)
    val swiftCode: String
}
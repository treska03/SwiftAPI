package io.github.treska.swiftapi.dto

data class SwiftCreateRequestDto(
    override val address: String,
    override val bankName: String,
    override val countryISO2: String,
    override val countryName: String,
    override val isHeadquarter: Boolean,
    override val swiftCode: String
) : BankDetailsDto
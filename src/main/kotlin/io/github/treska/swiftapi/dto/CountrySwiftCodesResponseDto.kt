package io.github.treska.swiftapi.dto

data class CountrySwiftCodesResponseDto(
    val countryISO2: String,
    val countryName: String,
    val swiftCodes: List<BankDetailsDto>
)

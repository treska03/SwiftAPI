package io.github.treska.swiftapi.dto

interface BankDetailsDto {
    val address: String
    val bankName: String
    val countryISO2: String
    val countryName: String
    val isHeadquarter: Boolean
    val swiftCode: String
}
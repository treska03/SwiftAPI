package io.github.treska.swiftapi.service

import io.github.treska.swiftapi.dto.BankDetailsDto
import io.github.treska.swiftapi.dto.CountrySwiftCodesResponseDto
import io.github.treska.swiftapi.dto.DefaultResponseDto
import org.springframework.stereotype.Service

@Service
class SwiftService(
    private val bankService: BankService
) {
    fun getByCode(code: String): BankDetailsDto {
        return bankService.getByCode(code)
    }

    fun getByCountry(countryCode: String): CountrySwiftCodesResponseDto {
        return bankService.getAllForCountry(countryCode)
    }

    fun create(
        address: String,
        bankName: String,
        countryISO2: String,
        countryName: String,
        isHeadquarter: Boolean,
        swiftCode: String
    ): DefaultResponseDto {
        bankService.create(
            address = address,
            bankName = bankName,
            countryISO2 = countryISO2,
            countryName = countryName,
            isHeadquarter = isHeadquarter,
            swiftCode = swiftCode
        )

        return DefaultResponseDto("All went ok.")
    }

    fun delete(swiftCode: String): DefaultResponseDto {
        bankService.delete(code = swiftCode)

        return DefaultResponseDto("All went ok.")
    }
}
package io.github.treska.swiftapi.controller

import io.github.treska.swiftapi.dto.BankDetailsDto
import io.github.treska.swiftapi.dto.CountrySwiftCodesResponseDto
import io.github.treska.swiftapi.dto.DefaultResponseDto
import io.github.treska.swiftapi.dto.SwiftCreateRequestDto
import io.github.treska.swiftapi.service.SwiftService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/swift-codes")
class SwiftController(
    private val swiftService: SwiftService
) {
    @GetMapping("/{swift-code}")
    fun getDetails(@PathVariable("swift-code") code: String): BankDetailsDto {
        return swiftService.getByCode(code)
    }

    @GetMapping("/country/{countryISO2code}")
    fun getSwiftCodesForCountry(@PathVariable("countryISO2code") countryCode: String): CountrySwiftCodesResponseDto {
        return swiftService.getByCountry(countryCode)
    }

    @PostMapping
    fun addSwiftCode(@RequestBody swiftCreateDto: SwiftCreateRequestDto): DefaultResponseDto {
        return swiftService.create(
            address = swiftCreateDto.address,
            bankName = swiftCreateDto.bankName,
            countryISO2 = swiftCreateDto.countryISO2,
            countryName = swiftCreateDto.countryName,
            isHeadquarter = swiftCreateDto.isHeadquarter,
            swiftCode = swiftCreateDto.swiftCode
        )
    }

    @DeleteMapping("/{swift-code}")
    fun deleteSwiftCode(@PathVariable("swift-code") code: String): DefaultResponseDto {
        return swiftService.delete(code)
    }
}
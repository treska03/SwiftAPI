package io.github.treska.swiftapi.service

import io.github.treska.swiftapi.dto.BankDetailsDto
import io.github.treska.swiftapi.dto.BranchResponseDto
import io.github.treska.swiftapi.dto.CountrySwiftCodesResponseDto
import io.github.treska.swiftapi.dto.HeadquarterResponseDto
import io.github.treska.swiftapi.exception.BankNotFoundException
import io.github.treska.swiftapi.model.Bank
import io.github.treska.swiftapi.repository.BankRepository
import org.springframework.stereotype.Service

@Service
class BankService(
    private val bankRepository: BankRepository, private val countryService: CountryService
) {
    fun getByCode(code: String): BankDetailsDto {
        val bank = bankRepository.findBySwiftCode(code)
            ?: throw BankNotFoundException("Bank with swiftCode=[$code] not found.")

        return bank.toDto()
    }

    fun getAllForCountry(countryCode: String): CountrySwiftCodesResponseDto {
        val country = countryService.getCountryByCode(countryCode)
        val swiftCodes = bankRepository.findByCountry(country).map { it.toDto() }

        return CountrySwiftCodesResponseDto(
            countryISO2 = country.code, countryName = country.name, swiftCodes = swiftCodes
        )
    }

    fun create(
        address: String,
        bankName: String,
        countryISO2: String,
        countryName: String,
        isHeadquarter: Boolean,
        swiftCode: String
    ) {
        val country = countryService.getCountryByCode(countryISO2)

        val bank = Bank(
            address = address,
            bankName = bankName,
            country = country,
            isHeadquarter = isHeadquarter,
            swiftCode = swiftCode
        )

        bankRepository.save(bank)
    }

    fun delete(code: String) {
        val bank = bankRepository.findBySwiftCode(code)
            ?: throw BankNotFoundException("Bank with swiftCode=[$code] not found.")

        bankRepository.delete(bank)
    }

    private fun findBranches(headquarter: Bank): List<Bank> {
        return bankRepository.findBySwiftCodeStartingWith(headquarter.swiftCode.take(8)).filter { !it.isHeadquarter }
    }

    private fun Bank.toDto(): BankDetailsDto =
        if (!isHeadquarter) {
            BranchResponseDto.from(this)
        } else {
            HeadquarterResponseDto.from(this, findBranches(this))
        }
}
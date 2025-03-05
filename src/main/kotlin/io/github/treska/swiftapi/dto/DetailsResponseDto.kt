package io.github.treska.swiftapi.dto

import io.github.treska.swiftapi.model.Bank

data class HeadquarterResponseDto(
    override val address: String,
    override val bankName: String,
    override val countryISO2: String,
    override val countryName: String,
    override val isHeadquarter: Boolean,
    override val swiftCode: String,
    val branches: List<BranchResponseDto>
) : BankDetailsDto {
    companion object {
        fun from(bank: Bank, branches: List<Bank>): HeadquarterResponseDto {
            return HeadquarterResponseDto(
                bank.address,
                bank.bankName,
                bank.country.code,
                bank.country.name,
                bank.isHeadquarter,
                bank.swiftCode,
                branches.map { BranchResponseDto.from(it) }
            )
        }
    }
}

data class BranchResponseDto(
    override val address: String,
    override val bankName: String,
    override val countryISO2: String,
    override val countryName: String,
    override val isHeadquarter: Boolean,
    override val swiftCode: String
) : BankDetailsDto {
    companion object {
        fun from(bank: Bank): BranchResponseDto {
            return BranchResponseDto(
                bank.address,
                bank.bankName,
                bank.country.code,
                bank.country.name,
                bank.isHeadquarter,
                bank.swiftCode,
            )
        }
    }
}

package io.github.treska.swiftapi.repository

import io.github.treska.swiftapi.model.Bank
import io.github.treska.swiftapi.model.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankRepository : JpaRepository<Bank, Long> {
    fun findBySwiftCode(swiftCode: String): Bank?
    fun findByCountry(country: Country): List<Bank>
    fun findBySwiftCodeStartingWith(swiftCode: String): List<Bank>
    fun existsBySwiftCode(swiftCode: String): Boolean
}
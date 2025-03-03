package io.github.treska.swiftapi.repository

import io.github.treska.swiftapi.model.Bank
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankRepository : JpaRepository<Bank, Long> {
    fun findBySwiftCode(swiftCode: String): Bank?
    fun findByBankName(swiftCode: String): List<Bank>
}
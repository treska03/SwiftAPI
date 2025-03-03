package io.github.treska.swiftapi.repository

import io.github.treska.swiftapi.model.Country
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, Long> {
    fun findByName(countryName: String): Country?
}
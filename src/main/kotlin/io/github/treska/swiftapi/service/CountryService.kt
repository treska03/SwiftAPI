package io.github.treska.swiftapi.service

import io.github.treska.swiftapi.exception.CountryNotFoundException
import io.github.treska.swiftapi.model.Country
import io.github.treska.swiftapi.repository.CountryRepository
import org.springframework.stereotype.Service

@Service
class CountryService(
    private val countryRepository: CountryRepository
) {
    fun getCountryByCode(code: String): Country {
        return countryRepository.findByCode(code) ?: throw CountryNotFoundException(
            "Country with code=[${code}] is not found."
        )
    }
}
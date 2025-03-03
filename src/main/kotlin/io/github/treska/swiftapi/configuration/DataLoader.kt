package io.github.treska.swiftapi.configuration

import io.github.treska.swiftapi.exception.SwiftCodesCSVNotFoundException
import io.github.treska.swiftapi.model.Bank
import io.github.treska.swiftapi.model.Country
import io.github.treska.swiftapi.repository.BankRepository
import io.github.treska.swiftapi.repository.CountryRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.io.InputStreamReader

@Component
class DataLoader(
    private val bankRepository: BankRepository, private val countryRepository: CountryRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val count = bankRepository.count()
        if (count == 0L) {
            val csv =
                javaClass.classLoader.getResourceAsStream("swift_codes.csv") ?: throw SwiftCodesCSVNotFoundException()
            val records = CSVFormat.EXCEL.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get()
                .parse(InputStreamReader(csv))

            records.forEach { record ->
                bankRepository.save(record.toBank())
            }

            println("Data loaded successfully!")
        } else {
            println("Data already exists in the table.")
        }
    }

    private fun CSVRecord.toBank(): Bank {
        val swiftCode = get(1)

        val country = getOrCreateCountry(
            name = get(6), code = get(0)
        )

        return Bank(
            address = get(4),
            bankName = get(3),
            country = country,
            isHeadquarter = swiftCode.endsWith("XXX"),
            swiftCode = swiftCode
        )
    }

    private fun getOrCreateCountry(name: String, code: String): Country {
        return countryRepository.findByName(name) ?: countryRepository.save(
            Country(
                name = name, code = code
            )
        )
    }
}
import io.github.treska.swiftapi.exception.BankNotFoundException
import io.github.treska.swiftapi.model.Bank
import io.github.treska.swiftapi.model.Country
import io.github.treska.swiftapi.repository.BankRepository
import io.github.treska.swiftapi.service.BankService
import io.github.treska.swiftapi.service.CountryService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest(classes = [BankService::class])
class BankServiceTest {

    @Autowired
    private lateinit var bankService: BankService

    @MockitoBean
    private lateinit var bankRepository: BankRepository

    @MockitoBean
    private lateinit var countryService: CountryService

    private val swiftCode = "ABCDEFGH123"
    private val countryCode = "US"
    private val countryName = "United States"
    private val bankName = "Test Bank"
    private val address = "123 Main St"
    private val isHeadquarter = true

    private lateinit var bank: Bank
    private lateinit var country: Country

    @BeforeEach
    fun setUp() {
        country = Country(code = countryCode, name = countryName)
        bank = Bank(
            address = address,
            bankName = bankName,
            country = country,
            isHeadquarter = isHeadquarter,
            swiftCode = swiftCode
        )
    }

    @Test
    fun `getByCode should throw error if swiftCode is missing`() {
        // Arrange
        whenever(bankRepository.findBySwiftCode(swiftCode)).thenReturn(null)

        // Act & Assert
        val exception = assertThrows<BankNotFoundException> {
            bankService.getByCode(swiftCode)
        }
        assertEquals("Bank with swiftCode=[$swiftCode] not found.", exception.message)
    }

    @Test
    fun `getByCode should work properly if code is present in db`() {
        // Arrange
        whenever(bankRepository.findBySwiftCode(swiftCode)).thenReturn(bank)

        // Act
        val result = bankService.getByCode(swiftCode)

        // Assert
        assertNotNull(result)
        assertEquals(bankName, result.bankName)
        assertEquals(address, result.address)
        assertEquals(swiftCode, result.swiftCode)
    }

    @Test
    fun `getAllForCountry should work properly`() {
        // Arrange
        whenever(countryService.getCountryByCode(countryCode)).thenReturn(country)
        whenever(bankRepository.findByCountry(country)).thenReturn(listOf(bank))

        // Act
        val result = bankService.getAllForCountry(countryCode)

        // Assert
        assertEquals(countryCode, result.countryISO2)
        assertEquals(countryName, result.countryName)
        assertEquals(1, result.swiftCodes.size)
        assertEquals(swiftCode, result.swiftCodes[0].swiftCode)
    }

    @Test
    fun `create should work properly`() {
        // Arrange
        whenever(countryService.getCountryByCode(countryCode)).thenReturn(country)

        // Act
        bankService.create(address, bankName, countryCode, countryName, isHeadquarter, swiftCode)

        // Assert
        verify(bankRepository).save(any())
    }

    @Test
    fun `delete should throw error if swiftCode not matches`() {
        // Arrange
        whenever(bankRepository.findBySwiftCode(swiftCode)).thenReturn(null)

        // Act & Assert
        val exception = assertThrows<BankNotFoundException> {
            bankService.delete(swiftCode)
        }
        assertEquals("Bank with swiftCode=[$swiftCode] not found.", exception.message)
    }

    @Test
    fun `delete should work properly when swiftcode is present in db`() {
        // Arrange
        whenever(bankRepository.findBySwiftCode(swiftCode)).thenReturn(bank)

        // Act
        bankService.delete(swiftCode)

        // Assert
        verify(bankRepository).delete(bank)
    }
}
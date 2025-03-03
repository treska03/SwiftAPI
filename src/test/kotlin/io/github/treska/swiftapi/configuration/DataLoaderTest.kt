package io.github.treska.swiftapi.configuration

import io.github.treska.swiftapi.model.Bank
import io.github.treska.swiftapi.model.Country
import io.github.treska.swiftapi.repository.BankRepository
import io.github.treska.swiftapi.repository.CountryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.io.ByteArrayInputStream

class DataLoaderTest {
    private lateinit var bankRepository: BankRepository
    private lateinit var countryRepository: CountryRepository
    private lateinit var dataLoader: DataLoader

    @BeforeEach
    fun setUp() {
        bankRepository = mock(BankRepository::class.java)
        countryRepository = mock(CountryRepository::class.java)
        dataLoader = DataLoader(bankRepository, countryRepository)
    }

    @Test
    fun `test run when bank table is empty and csv content is provided`() {
        // given
        `when`(bankRepository.count()).thenReturn(0L)

        val mockClassLoader = mock(ClassLoader::class.java)
        val inputStream = ByteArrayInputStream(csvContent.toByteArray())
        `when`(mockClassLoader.getResourceAsStream("swift_codes.csv")).thenReturn(inputStream)

        val mockBank = mock(Bank::class.java)
        `when`(bankRepository.save(any())).thenReturn(mockBank)
        val mockCountry = mock(Country::class.java)
        `when`(countryRepository.save(any())).thenReturn(mockCountry)

        // when
        dataLoader.run()

        verify(bankRepository, atLeastOnce()).save(any())
    }

    @Test
    fun `test run when bank table is not empty`() {
        // given
        `when`(bankRepository.count()).thenReturn(10L)

        // when
        dataLoader.run()

        // then
        verify(bankRepository, never()).save(any())
    }

    @Test
    fun `test run when swift_codes csv does not exist`() {
        // given
        `when`(bankRepository.count()).thenReturn(0L)

        // when
        val mockClassLoader = mock(ClassLoader::class.java)
        `when`(mockClassLoader.getResourceAsStream("swift_codes.csv")).thenReturn(null)

        // then
        assertThrows<NullPointerException> { dataLoader.run() }
    }

    private val csvContent = """
            COUNTRY ISO2 CODE,SWIFT CODE,CODE TYPE,NAME,ADDRESS,TOWN NAME,COUNTRY NAME,TIME ZONE
            AL,AAISALTRXXX,BIC11,UNITED BANK OF ALBANIA SH.A,"HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023",TIRANA,ALBANIA,Europe/Tirane
            BG,ABIEBGS1XXX,BIC11,ABV INVESTMENTS LTD,"TSAR ASEN 20  VARNA, VARNA, 9002",VARNA,BULGARIA,Europe/Sofia
            BG,ADCRBGS1XXX,BIC11,ADAMANT CAPITAL PARTNERS AD,"JAMES BOURCHIER BLVD 76A HILL TOWER SOFIA, SOFIA, 1421",SOFIA,BULGARIA,Europe/Sofia
            UY,AFAAUYM1XXX,BIC11,AFINIDAD A.F.A.P.S.A.,"PLAZA INDEPENDENCIA 743  MONTEVIDEO, MONTEVIDEO, 11000",MONTEVIDEO,URUGUAY,America/Montevideo
        """.trimIndent()
}
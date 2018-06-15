package fr.jordanmarques.meteofranceapi.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

internal class MeteoServiceTest{

    @InjectMockKs
    lateinit var meteoService: MeteoService

    @MockK
    lateinit var dateProvider: DateProvider

    @Before
    fun init() = MockKAnnotations.init(this)

    @Test(expected = IllegalArgumentException::class)
    fun `should throw an exception when date is more than 2 days ago`() {

        every { dateProvider.now() } returns LocalDate.of(2015, 1, 10)

        meteoService.meteo("2015-01-07")

    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw an exception when date is more than 3 days further`() {

        every { dateProvider.now() } returns LocalDate.of(2015, 1, 10)

        meteoService.meteo("2015-01-14")

    }

}
package fr.jordanmarques.meteofranceapi.meteofranceapi.service


import khronos.Dates
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime


internal class MeteoFranceServiceTest {

    @InjectMocks
    lateinit var meteoFranceService: MeteoFranceService

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should return a string date used by meteo france api for a given date`() {
        val expected = "201806210100"
        val date = Dates.of(year = 2018, month = 6, day = 21, hour = 9, minute = 0)

        val result = meteoFranceService.queryDateFor(date)

        assertEquals(expected, result)
    }

    @Test
    fun `should return a string date used by meteo france api for now`() {
        val expected = "201806210900"

        val result = meteoFranceService.queryDateForNow(LocalDateTime.of(2018,  6, 21,  11, 32))

        assertEquals(expected, result)
    }
}

package fr.jordanmarques.meteofranceapi.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.spyk
import khronos.Dates
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import java.time.LocalDateTime
import java.util.*


internal class MeteoFranceServiceTest {

    @InjectMockKs
    lateinit var meteoFranceService: MeteoFranceService

    @Before
    fun init() = MockKAnnotations.init(this)

    @Test
    fun `should return a string date used by meteo france api for a given date`() {
        val expected = "201806211200"
        val date = Dates.of(year = 2018, month = 6, day = 21, hour = 9, minute = 0)

        val result = meteoFranceService.queryDateFor(date)

        assertEquals(expected, result)
    }

    @Test
    fun `should return a string date used by meteo france api for now`() {
        val expected = "201806210900"

        val result = meteoFranceService.queryDateForNow(LocalDateTime.of(2018, 6, 21, 11, 32))

        assertEquals(expected, result)
    }

    @Test
    fun `should return null temperature for a past meteo`() {

        val spyMeteoFranceService = spyk<MeteoFranceService>()

        val meteoFranceApiReturn = MeteoFrance(
                echeance = "201806130900",
                heureDeMiseAJour = "09h12",
                indiceConfFr = 5,
                previsionLieux = arrayListOf(
                        PrevisionLieux(
                                lieu = Lieu(id = "200040", nomAffiche = "Ajaccio", type = "VILLE_FRANCE"),
                                prevision = Prevision(tempMin = 17, tempMax = 23, temperature = 20, tempsSensible = "Éclaircies", pictoTemps = "J_W1_24-N_3"))
                )
        )

        every { spyMeteoFranceService.getFromMeteoFrance(any()) } returns meteoFranceApiReturn

        val meteoFrance = spyMeteoFranceService.meteoFor(Date())

        assertNull(meteoFrance.previsionLieux[0].prevision.temperature)

    }
}

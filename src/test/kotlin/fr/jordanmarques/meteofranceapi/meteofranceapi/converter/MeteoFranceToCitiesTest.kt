package fr.jordanmarques.meteofranceapi.meteofranceapi.converter

import fr.jordanmarques.meteofranceapi.meteofranceapi.service.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class MeteoFranceToCitiesTest {

    @InjectMocks
    lateinit var meteoFranceToCities: MeteoFranceToCities

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should convert api result to internal object`() {
        var meteoFranceApiReturn = MeteoFrance(
                echeance = "201806130900",
                heureDeMiseAJour = "09h12",
                indiceConfFr = 5,
                previsionLieux = arrayListOf(
                        PrevisionLieux(
                                lieu = Lieu(id = "200040", nomAffiche = "Ajaccio", type = "VILLE_FRANCE"),
                                prevision = Prevision(tempMin = 17, tempMax = 23, temperature = 20, tempsSensible = "Éclaircies", pictoTemps = "J_W1_24-N_3")),
                        PrevisionLieux(
                                lieu = Lieu(id = "372610", nomAffiche = "Tours", type = "VILLE_FRANCE"),
                                prevision = Prevision(tempMin = 13, tempMax = 22, temperature = 17, tempsSensible = "Risque d'orages", pictoTemps = "J_W1_0-N_1"))
                )
        )

        var cities = arrayListOf(
                City(name = "Ajaccio", lastUpdate = "09h12", temperature = Temperature(actual = 20, min = 17, max = 23)),
                City(name = "Tours", lastUpdate = "09h12", temperature = Temperature(actual = 17, min = 13, max = 22))
        )

        var result = meteoFranceToCities.convert(meteoFranceApiReturn)


        assertEquals(result, cities)

    }

}
package fr.jordanmarques.meteofranceapi.converter

import fr.jordanmarques.meteofranceapi.service.City
import fr.jordanmarques.meteofranceapi.service.MeteoFrance
import fr.jordanmarques.meteofranceapi.service.Temperature
import org.springframework.stereotype.Component

@Component
class MeteoFranceToCities {

    fun convert(meteoFrance: MeteoFrance): List<City> {

        return meteoFrance.previsionLieux
                .map { pv ->
                    City(
                            name = pv.lieu.nomAffiche,
                            lastUpdate = meteoFrance.heureDeMiseAJour,
                            temperature = Temperature(actual = pv.prevision.temperature, min = pv.prevision.tempMin, max = pv.prevision.tempMax),
                            temps = pv.prevision.tempsSensible
                    )
                }
    }
}

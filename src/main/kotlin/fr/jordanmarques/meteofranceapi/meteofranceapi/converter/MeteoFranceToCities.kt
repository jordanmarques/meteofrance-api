package fr.jordanmarques.meteofranceapi.meteofranceapi.converter

import fr.jordanmarques.meteofranceapi.meteofranceapi.service.City
import fr.jordanmarques.meteofranceapi.meteofranceapi.service.MeteoFrance
import fr.jordanmarques.meteofranceapi.meteofranceapi.service.Temperature
import org.springframework.stereotype.Component

@Component
class MeteoFranceToCities {

    fun convert(meteoFrance: MeteoFrance): List<City> {

        return meteoFrance.previsionLieux
                .map { pv ->
                    City(
                            name = pv.lieu.nomAffiche,
                            lastUpdate = meteoFrance.heureDeMiseAJour,
                            temperature = Temperature(actual = pv.prevision.temperature, min = pv.prevision.tempMin, max = pv.prevision.tempMax)
                    )
                }
    }

}

package fr.jordanmarques.meteofranceapi.meteofranceapi.service

import org.springframework.stereotype.Component

@Component
class MeteoService {

}

data class Temperature(val actual: Int?, val min: Int, val max: Int)
data class City(val name: String, val lastUpdate: String, val temperature: Temperature)
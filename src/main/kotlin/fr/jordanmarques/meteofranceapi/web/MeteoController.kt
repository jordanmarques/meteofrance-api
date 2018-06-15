package fr.jordanmarques.meteofranceapi.web

import fr.jordanmarques.meteofranceapi.service.City
import fr.jordanmarques.meteofranceapi.service.MeteoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MeteoController {

    @Autowired
    lateinit var meteoService: MeteoService

    @GetMapping("/meteo")
    fun getMeteo(@RequestParam(value = "date") date: String?): List<City> {
        return if (date != null) meteoService.meteo(date) else meteoService.meteo()
    }
}
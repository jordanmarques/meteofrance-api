package fr.jordanmarques.meteofranceapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MeteofranceApiApplication

fun main(args: Array<String>) {
    runApplication<MeteofranceApiApplication>(*args)
}

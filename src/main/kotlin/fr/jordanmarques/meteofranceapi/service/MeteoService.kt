package fr.jordanmarques.meteofranceapi.service

import fr.jordanmarques.meteofranceapi.converter.MeteoFranceToCities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class MeteoService {

    @Autowired
    lateinit var meteofranceService: MeteoFranceService

    @Autowired
    lateinit var meteofranceToCities: MeteoFranceToCities

    @Autowired
    lateinit var dateProvider: DateProvider

    fun meteo(stringDate: String? = null): List<City> {

        val meteoFranceData = when (stringDate) {
            null -> meteo()
            else -> meteo(stringDate)
        }

        return meteofranceToCities.convert(meteoFranceData)
    }

    private fun meteo(stringDate: String): MeteoFrance {

        if (!isDateinRange(stringDate.toLocalDate()))
            throw IllegalArgumentException("Date should be between 2 past days and 3 further days")

        return meteofranceService.meteo(stringDate.toDate())
    }

    private fun meteo() = meteofranceService.meteo()

    private fun isDateinRange(desiredDate: LocalDate): Boolean {

        val twoDayBeforToday = dateProvider.now().minusDays(2)
        val threeDaysAfterToday = dateProvider.now().plusDays(3)

        val isPastGapOk = twoDayBeforToday until desiredDate in 0..2
        val isFuturGapOk = desiredDate until threeDaysAfterToday in 0..3

        return  isPastGapOk || isFuturGapOk

    }

    private fun String.toDate(): Date {
        return this.toLocalDate().toDate()
    }

    private fun String.toLocalDate(): LocalDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)

    infix fun LocalDate.until(date: LocalDate): Long {
        return ChronoUnit.DAYS.between(this, date)
    }
}

data class Temperature(val actual: Int?, val min: Int?, val max: Int?)
data class City(val name: String?, val lastUpdate: String?, val temperature: Temperature?, val temps: String?)
package fr.jordanmarques.meteofranceapi.meteofranceapi.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class MeteoFranceService {

    fun meteo(date: Date?): MeteoFrance {

        val queryDate = if (date == null) queryDateForNow() else queryDateFor(date)

        val response = khttp.get(url = "http://www.meteofrance.com/mf3-rpc-portlet/rest/carte/france/PAYS/PAYS007?echeance=$queryDate&nightMode=false")
        return jacksonObjectMapper().readValue(response.text)
    }

    fun queryDateForNow(now: LocalDateTime = LocalDateTime.now()): String {

        val hour = when (now.hour) {
            in 0..8 -> "00"
            in 9..11 -> "09"
            in 12..14 -> "12"
            in 15..17 -> "15"
            in 18..20 -> "18"
            in 21..23 -> "21"
            else -> "01"
        }

        return formatDate(year = now.year, month = now.monthValue, day = now.dayOfMonth, hour = hour)
    }

    fun queryDateFor(date: Date): String {
        val localDate = date.toInstant().atZone(ZoneId.systemDefault())

        return formatDate(year = localDate.year, month = localDate.monthValue, day = localDate.dayOfMonth)
    }

    fun formatDate(year: Int, month: Int, day: Int, hour: String = "01"): String {
        val month = if (month in 1..9) "0$month" else "$month"
        val day = if (day in 1..9) "0$day" else "$day"

        return "$year$month$day${hour}00"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class MeteoFrance(val echeance: String, val heureDeMiseAJour: String, val indiceConfFr: Int, val previsionLieux: List<PrevisionLieux>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PrevisionLieux(val lieu: Lieu, val prevision: Prevision)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Prevision(val tempMin: Int, val tempMax: Int, val temperature: Int?, val tempsSensible: String, val pictoTemps: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Lieu(val id: String, val nomAffiche: String, val type: String)
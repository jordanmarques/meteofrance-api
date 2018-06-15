package fr.jordanmarques.meteofranceapi.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import fr.jordanmarques.meteofranceapi.extension.fetch
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class MeteoFranceService {

    fun meteo(date: Date? = null): MeteoFrance {

        return date?.let { meteoFor(date) } ?: actualMeteo()
    }

    private fun actualMeteo(): MeteoFrance {
        return getFromMeteoFrance(queryDateForNow())
    }

    fun meteoFor(date: Date): MeteoFrance {
        return getFromMeteoFrance(queryDateFor(date)).sanitizeNonActualMeteo()
    }

    fun getFromMeteoFrance(queryDate: String): MeteoFrance {
        return fetch(url = "http://www.meteofrance.com/mf3-rpc-portlet/rest/carte/france/PAYS/PAYS007?echeance=$queryDate&nightMode=false")
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

    fun formatDate(year: Int, month: Int, day: Int, hour: String = "12"): String {
        val stringMonth = if (month in 1..9) "0$month" else "$month"
        val stringDay = if (day in 1..9) "0$day" else "$day"

        return "$year$stringMonth$stringDay${hour}00"
    }

    private fun MeteoFrance.sanitizeNonActualMeteo(): MeteoFrance {
        this.previsionLieux = this.previsionLieux.sanitizePrevisionLieu()
        return this
    }

    private fun List<PrevisionLieux>.sanitizePrevisionLieu(): List<PrevisionLieux> {
        return this.map { pl ->
            val newPrevision = pl.prevision
            newPrevision.temperature = null
            PrevisionLieux(lieu = pl.lieu, prevision = newPrevision)
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class MeteoFrance(val echeance: String, val heureDeMiseAJour: String, val indiceConfFr: Int, var previsionLieux: List<PrevisionLieux>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PrevisionLieux(val lieu: Lieu, val prevision: Prevision)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Prevision(val tempMin: Int, val tempMax: Int?, var temperature: Int?, val tempsSensible: String?, val pictoTemps: String?)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Lieu(val id: String?, val nomAffiche: String?, val type: String?)
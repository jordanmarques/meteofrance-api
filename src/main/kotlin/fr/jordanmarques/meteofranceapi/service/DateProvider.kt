package fr.jordanmarques.meteofranceapi.service

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Component
class DateProvider {
    fun now(): LocalDate {
        return LocalDate.now()
    }
}

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}
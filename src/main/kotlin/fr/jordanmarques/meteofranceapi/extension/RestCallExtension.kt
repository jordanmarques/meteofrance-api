package fr.jordanmarques.meteofranceapi.extension

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

inline fun <reified T : Any> fetch(url: String): T = jacksonObjectMapper().readValue(khttp.get(url = url).text)


package com.ecoheat.Apis.GoogleCalendarApi
import io.github.cdimascio.dotenv.dotenv
import okhttp3.*
import org.springframework.context.MessageSource
import java.io.IOException
import java.util.*
class GoogleCalendarApi {
    val httpClient = OkHttpClient()

    // ID de um calendário público do Google que contém eventos de feriados brasileiros
    val publicCalendarId = "pt-br.brazilian#holiday@group.v.calendar.google.com"

    // URL para listar os eventos do calendário público
    val url = "https://www.googleapis.com/calendar/v3/calendars/$publicCalendarId/events"

    // Fazendo uma solicitação GET para obter os eventos do calendário público
    val request = Request.Builder()
        .url(url)
        .build()

    val response = httpClient.newCall(request).execute()


}
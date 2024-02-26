package com.ecoheat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.stereotype.Component
import java.util.*

@SpringBootApplication
class EcoheatApplication

fun main(args: Array<String>) {
	val context = runApplication<EcoheatApplication>(*args)

	val messageSource = context.getBean(MessageSource::class.java)
	val locale = Locale("pt")

	val startupMessage = messageSource.getMessage("startup.message", null, locale)
	println(startupMessage)

}

package com.ecoheat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcoheatApplication

fun main(args: Array<String>) {
	runApplication<EcoheatApplication>(*args)
	println("Server started")
}

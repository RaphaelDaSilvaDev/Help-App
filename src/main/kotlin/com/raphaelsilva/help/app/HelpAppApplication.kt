package com.raphaelsilva.help.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelpAppApplication

fun main(args: Array<String>) {
	runApplication<HelpAppApplication>(*args)
}

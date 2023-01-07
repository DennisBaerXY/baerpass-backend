package com.dennisdevelops

import com.dennisdevelops.plugins.*
import com.dennisdevelops.repositorys.repositoryModule
import com.dennisdevelops.services.servicesModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit =
	io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
	install(Koin){
		SLF4JLogger()
		modules(repositoryModule, servicesModule)
	}

	configureDatabase()
	configureSecurity()
	configureHTTP()
	configureMonitoring()
	configureSerialization()
	configureRouting()
}

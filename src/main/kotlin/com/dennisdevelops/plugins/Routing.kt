package com.dennisdevelops.plugins

import com.dennisdevelops.routes.authRoutes
import com.dennisdevelops.routes.passwordManagementRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
	install(StatusPages) {
		exception<Throwable> { call, cause ->
			call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
		}
	}

	routing {
		authRoutes()
		passwordManagementRoutes()

		get("/") {
			call.respondText("Hello World!")
		}
	}
}

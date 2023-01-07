package com.dennisdevelops.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.passwordManagementRoutes() {
	authenticate("user_auth") {
		route("/passwords") {
			get {

				call.respond("Hello World!")
			}
		}
	}


}
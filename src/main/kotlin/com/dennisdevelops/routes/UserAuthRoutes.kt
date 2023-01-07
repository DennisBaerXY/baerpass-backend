package com.dennisdevelops.routes

import com.dennisdevelops.dtos.UserDTO
import com.dennisdevelops.models.UserSession
import com.dennisdevelops.repositorys.SessionRepository
import com.dennisdevelops.services.AuthenticationService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject


fun Route.authRoutes() {

	val authenticationService by inject<AuthenticationService>()
	val sessionRepository = SessionRepository()
	route("/auth") {
		post("/register") {
			val userData = call.receive<UserDTO>()
			val user = authenticationService.registerUser(userData.username, userData.email, userData.password)

			if (user != null) {
				val session = sessionRepository.createSession(user.id!!).let {
					UserSession(it!!.sessionId, it.userId, it.username)
				}
				call.sessions.set(session)
				call.respond("User has successfully been registered")
			} else {
				call.respond("User already exists")
			}

		}

		post("/login") {
			val userData = call.receive<UserDTO>()
			val user = authenticationService.loginUser(userData.email, userData.password)
			val session = sessionRepository.createSession(user.id!!).let {
				UserSession(it!!.sessionId, it.userId, it.username)
			}
			call.sessions.set(session)
			call.respond("User has successfully been logged in")
		}


	}
}
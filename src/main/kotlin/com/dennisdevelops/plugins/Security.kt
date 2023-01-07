package com.dennisdevelops.plugins

import com.dennisdevelops.models.UserSession
import com.dennisdevelops.repositorys.SessionRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import java.io.File
import kotlin.collections.set
import kotlin.time.Duration

fun Application.configureSecurity() {

	install(Sessions) {
		// load from environment variable or use default
		val secretEncryptKey = hex(
			this@configureSecurity.environment.config.property("auth.sessions.encryptionKey")
				.getString()
		)
		val secretDecryptionKey = hex(
			this@configureSecurity.environment.config.property("auth.sessions.decryptionKey")
				.getString()
		)

		cookie<UserSession>(
			"user_auth",
			storage = directorySessionStorage(File("build/.sessions"))
		) {
			cookie.path = "/"
			cookie.maxAge = Duration.INFINITE
			cookie.extensions["SameSite"] = "lax"

			transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretDecryptionKey));

		}
	}

	authentication() {

		val sessionRepository = SessionRepository();

		session<UserSession>("user_auth") {
			validate { session ->

				val sessionData = sessionRepository.getSession(session.sessionId)

				if (sessionData != null) {
					 sessionData
				} else {
					null
				}
			}
		}
	}

}

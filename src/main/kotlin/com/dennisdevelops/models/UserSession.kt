package com.dennisdevelops.model

import io.ktor.server.auth.*

data class UserSession(val userId: String, val username: String, val email: String, val token: String) : Principal

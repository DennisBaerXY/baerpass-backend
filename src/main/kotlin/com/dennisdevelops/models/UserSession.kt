package com.dennisdevelops.models

import io.ktor.server.auth.*

// The user is registerd by an email as username and password
data class UserSession(val sessionId: Int,val userId: Int, val username:String?) : Principal

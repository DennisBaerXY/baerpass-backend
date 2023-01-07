package com.dennisdevelops.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(val username:String, val email: String, val password: String)

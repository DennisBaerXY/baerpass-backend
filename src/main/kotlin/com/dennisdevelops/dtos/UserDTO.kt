package com.dennisdevelops.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDTO(val email: String, val password: String)

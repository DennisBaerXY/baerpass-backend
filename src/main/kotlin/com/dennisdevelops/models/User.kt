package com.dennisdevelops.models

data class User(val id: Int?, val username: String, val email: String, val password: String, val salt: String, val hashing_algorithm: String)


